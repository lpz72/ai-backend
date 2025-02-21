package org.lpz.ai;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lpz.ai.mapper.ContentMapper;
import org.lpz.ai.mapper.UserMapper;
import org.lpz.ai.model.domain.Content;
import org.lpz.ai.model.domain.User;
import org.lpz.ai.service.ContentService;
import org.lpz.ai.service.UserService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class MysqlTest {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;
    @Resource
    private ContentService contentService;
    @Resource
    private ContentMapper nlpContentMapper;

    @Test
    void userList() {
        List<User> list = userService.list();

        Assertions.assertEquals(5,list.size());
    }
    @Test
    void userSelect() {
        List<User> list = userService.list();
        System.out.println(list.size());
    }

    @Test
    void userInsert() {
        String userAccount = "lpz1";
        String password = "12345678";

        String encryptPassword = DigestUtils.md5DigestAsHex(("lpz" + password).getBytes());
        User user = new User();
        user.setUsername("lpz");
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setGender(0);
        user.setPhone("1111111");
        user.setEmail("22222222");
        user.setPlanetCode("1");


        userService.save(user);
    }
    @Test
    void contentInsert() {
        for (int i = 0;i <100;i ++) {
            Content content = new Content();
            content.setContent("百度是一家人工智能公司");
            content.setType("文本纠错");
            content.setCreateTime(new Date());
            content.setModifyTime(new Date());
            content.setUserId(2L);
            contentService.save(content);
        }


    }
//
//    }
//    @Test
//    void NlpInsert() {
//        Content nlpContent = new Content();
//        nlpContent.setId(1);
//        nlpContent.setContent("1111");
//        nlpContent.setType(1);
//        nlpContent.setCreateTime(new Date());
//        nlpContent.setModifyTime(new Date());
//
//        nlpContentMapper.insert(nlpContent);
//
//
//    }


}
