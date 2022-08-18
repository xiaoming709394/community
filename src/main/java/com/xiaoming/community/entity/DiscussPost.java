package com.xiaoming.community.entity;

import lombok.Data;

import java.util.Date;

/**
 * 帖子信息实体
 *
 * @author 赵明城
 * @date 2022/8/6
 */
@Data
public class DiscussPost {

    /**
     * id
     */
    private int id;

    /**
     * 用户id
     */
    private int userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 帖子类型
     */
    private int type;

    /**
     * 帖子状态
     */
    private int status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 评论数量
     */
    private int commentCount;

    /**
     * 分数
     */
    private double score;
}
