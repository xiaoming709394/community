package com.xiaoming.community.entity;

import lombok.Data;

import java.util.Date;

/**
 * 评论实体
 *
 * @author 赵明城
 * @date 2022/9/12
 */
@Data
public class Comment {

    /**
     * id
     */
    private int id;

    /**
     * 用户id
     */
    private int userId;

    /**
     * 评论类型
     */
    private int entityType;

    /**
     * 帖子ID
     */
    private int entityId;

    /**
     * 评论目标id
     */
    private int targetId;

    /**
     * 内容
     */
    private String content;

    /**
     * 状态
     */
    private int status;

    /**
     * 创建日期
     */
    private Date createTime;

}
