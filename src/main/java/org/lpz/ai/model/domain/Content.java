package org.lpz.ai.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 识别记录表
 * @TableName content
 */
@TableName(value ="content")
@Data
public class Content implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 识别内容
     */
    private String content;

    /**
     * 类型
     */
    private String type;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date modifyTime;

    /**
     * 1-删除
     */
    private Integer isDelete;

    /**
     * 用户id
     */
    private Long userId;
}