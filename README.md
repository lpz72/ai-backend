# AI模型

## 技术栈

### 前端：

Vue3

vue-cli脚手架

Ant Design Pro

### 后端

spring boot

Swagger + Knife4j接口文档



## 前端初始化

使用Vue-CLI脚手架快速创建Vue3的项目：https://cli.vuejs.org/zh/

为什么选择该脚手架？

1. 常用的标准脚手架，开源、并且star数多
2. 目前进入维护模式，相对稳定，对低版本的Node兼容性好，不容易出现因为环境不同而导致的问题
3. 相对轻量，整合了一些前端项目开发常用的工具，并且可以按需选取
   该脚手架自动整合了vue-router.路由、TypeScript、前端工程化等库：

安装脚手架Vue CLI（参考官方文档）：

```bash
npm install -g @vue/cli
#检测是否安装成功
vue -V 
```

在项目目录下进入cmd：

```bash
#创建项目，名字为ai-frontend
vue create ai-frontend
```

![image-20250218213630784](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20250218213630784.png)

![image-20250218213646700](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20250218213646700.png)

#### 引入Ant Design Pro

官方文档：https://www.antdv.com/docs/vue/getting-started-cn

终端下运行

```cmd
npm i --save ant-design-vue@4.x
```

使用全局完整注册：

在main.ts中引入：

```ts
import { createApp } from 'vue';
import Antd from 'ant-design-vue';
import App from './App';
import 'ant-design-vue/dist/reset.css';

const app = createApp(App);

app.use(Antd).mount('#app');
```



## 后端初始化

创建spring boot 项目，并选择相应的依赖

![image-20250218213751676](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20250218213751676.png)

application改为yml文件，格式更规范，配置数据库等

```xml
spring:
  application:
    name: ai
    # DataSource Config
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: lpz
    password: lpz2003...
    url: jdbc:mysql://localhost:3306/ai
server:
  port: 8080
  servlet:
    context-path: /api

mybatis-plus:
  configuration:
    # mybatis 输出日志
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
    # MyBatis 配置,取消下划线转驼峰
    map-underscore-to-camel-case: false
  # 配置逻辑删除
  global-config:
    db-config:
      logic-delete-field: isDelete # 全局逻辑删除字段名
      logic-delete-value: 1 # 逻辑已删除值
      logic-not-delete-value: 0 # 逻辑未删除值
```



## 后端

### 整合Swagger + Knife4j接口文档

```java
package org.lpz.usercenter.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

// 标明是配置类
@Configuration
// 开启Swagger功能
@EnableSwagger2WebMvc
//@Profile("prod")
public class SwaggerConfig {
    /**
     * 构建一个Docket Bean
     * @return
     */
    @Bean
    public Docket createRestapi() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 页面展示信息
                .apiInfo(apiInfo())
                // 返回一个ApiSelectorBuilder实例，用来控制接口被Swagger做成文档
                .select()
                // 用于指定扫描哪个包下的接口
                .apis(RequestHandlerSelectors.basePackage("org.lpz.usercenter.controller"))
                // 选择所有的API，如果你只想为部分API生成文档，可以配置这里
                .paths(PathSelectors.any())
                .build();
    }
    /**
     * 定义api文档主页面的基本信息
     * 访问地址：http://项目实际地址/swagger-ui.html
     * 或者Knife4j：http://localhost:8080/api/doc.html
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 页面标题
                .title("用户中心")
                .description("用户中心接口文档")
                .contact("lpz")
                // 版本号
                .version("1.0")
                .build();
    }
}
```

```xml
<!--        knife4j-->
        <dependency>
            <groupId>com.github.xiaoymin</groupId>
            <artifactId>knife4j-openapi2-spring-boot-starter</artifactId>
            <version>4.4.0</version>
        </dependency>
```

引入mybatis-plus依赖

```xml
<!--mybatis-plus框架，版本降到3.4.2左右可以-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.4.2</version>
</dependency>
```

### 配置跨域

```xml
package org.lpz.usercenter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
 
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域的路径
        registry.addMapping("/**")
                //设置允许跨域请求的域名
                //当 **Credentials为true时，** Origin不能为星号，需为具体的ip地址【如果接口不带cookie,ip无需设成具体ip】
                .allowedOriginPatterns("*")
                //是否允许证书 不再默认开启
                .allowCredentials(true)
                //设置允许的方法
                .allowedMethods("*")
                .allowedHeaders("*")
                //跨域允许时间
                .maxAge(3600);
    }
}

```

### 引入百度智能云依赖，会发生slf4j冲突，排除其中的slf4j

```xml
<dependency>
            <groupId>com.baidu.aip</groupId>
            <artifactId>java-sdk</artifactId>
            <version>4.15.4</version>
            <exclusions>
                <exclusion>
                    <groupId>org.slf4j</groupId>
                    <artifactId>slf4j-simple</artifactId>
                </exclusion>
            </exclusions>
</dependency>
```

### 数据库设计

nlp纠错数据表

```sql
create table content
(
    id         int auto_increment comment '主键id'
        primary key,
    content    text          null comment '识别内容',
    type       varchar(256)  null comment '类型',
    createTime datetime      null on update CURRENT_TIMESTAMP comment '创建时间',
    modifyTime datetime      null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   int default 0 not null comment '1-删除',
    userId     int           not null comment '用户id'
)
    comment '识别记录表' charset = utf8mb3;

```

用户表设计：

```sql
create table user
(
    id           bigint auto_increment comment 'id'
        primary key,
    username     varchar(256)                       null comment '用户昵称',
    userAccount  varchar(256)                       null comment '登录账号',
    avatarUrl    varchar(1024)                      null comment '头像',
    userPassword varchar(256)                       null comment '密码',
    gender       tinyint                            null comment '性别',
    phone        varchar(256)                       null comment '电话',
    email        varchar(256)                       null comment '邮箱',
    userStatus   int      default 0                 not null comment '用户状态 0:正常',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null comment '更新时间',
    isDelete     tinyint  default 0                 not null comment '逻辑删除',
    userRole     int      default 0                 not null comment '用户角色 0：普通用户 1：管理员',
    planetCode   varchar(512)                       null comment '编号'
);

```

使用mybatisx插件生成代码

![image-20250218232059707](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20250218232059707.png)

并在AiApplication上加注解

```java
@MapperScan("org.lpz.ai.mapper")
```



### 引入百度智能云的三个功能：

文字识别

图片识别

文本纠错



## 前端

头部导航栏：GlobalHeader

基础布局：BasiclLayout

引入[Vue Router](https://router.vuejs.org/zh/introduction.html)路由库,根据路由动态展示内容



引入axios请求库,使用axios发送请求

安装请求工具类Axios

官方文档：https://axios-http.com/zh/docs/intro

```bash
npm install axios
```

```ts
import axios from "axios";

const myAxios = axios.create({
    baseURL: "http://localhost:8080/api",
    timeout: 10000,
    withCredentials: true,
});

// 添加请求拦截器
myAxios.interceptors.request.use(
    function (config) {
        // 在发送请求之前做些什么
        return config;
    },
    function (error) {
        // 对请求错误做些什么
        return Promise.reject(error);
    }
);

// 添加响应拦截器
myAxios.interceptors.response.use(
    function (response) {
        // 2xx 范围内的状态码都会触发该函数。
        // 对响应数据做点什么
        console.log(response);

        const { data } = response;
        console.log(data);
        return response.data;
    },
    function (error) {
        // 超出 2xx 范围的状态码都会触发该函数。
        // 对响应错误做点什么
        return Promise.reject(error);
    }
);

export default myAxios;
```

编写前端页面：

三个页面

## 文本纠错

### 前端

NlpPage

使用输入框组件，按钮绑定事件，向后端发送请求

### 后端

编写相应接口

## 文字识别

### 前端

WordPage

使用上传组件，向后端发送请求

### 后端

编写相应接口

## 图像识别

### 前端

ImgPage

使用上传组件，向后端发送请求

### 后端

编写相应接口

## 历史记录

### 前端

使用分页表格组件，删除功能

### 后端

编写分页查询接口，根据当前用户id查询

后端删除功能使用post请求，**封装请求体**

## 登录/注册

### redis存储session

1. 引入redis依赖（最好与springboot的版本一致）：

```xml
<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-redis -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
    <version>2.6.13</version>
</dependency>
```

2. 引入spring-session和redis的整合，使得自动将session存储到redis中

```xml
<!-- https://mvnrepository.com/artifact/org.springframework.session/spring-session-data-redis -->
<dependency>
    <groupId>org.springframework.session</groupId>
    <artifactId>spring-session-data-redis</artifactId>
    <version>2.6.3</version>
</dependency>

```

3.在后端配置文件中添加redis配置：

```xml
  # redis配置
  redis:
    port: 6979
    host: localhost
    database: 0
```

redis管理工具：quick redis：https://quick123.net/

4. 修改spring-session存储配置`spring.session.store-type`,

   默认是none，表示存储在单台服务器

   store-type:redis，表示从redis读写session

5. 自定义序列化器

   为了防止写入Redis的数据乱码、浪费空间等，可以自定义序列化器：

   ```java
   package com.lpz.ai.config;
   
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   import org.springframework.data.redis.connection.RedisConnectionFactory;
   import org.springframework.data.redis.core.RedisTemplate;
   import org.springframework.data.redis.serializer.RedisSerializer;
   
   @Configuration
   public class RedisTemplateConfig {
   
       @Bean
       public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
           RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
           redisTemplate.setConnectionFactory(connectionFactory);
           redisTemplate.setKeySerializer(RedisSerializer.string());
           return redisTemplate;
       }
   }
   
   ```

   > 存入到redis里的信息也要序列化，比如该项目中存入user对象，则User类就需要序列化

### 前端

使用表单组件

### 后端

编写controller、service、Impl

## 部署
使用宝塔部署
