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

    private int id;

    private int userId;

    private String title;

    private String content;

    private int type;

    private int status;

    private Date createTime;

    private int commentCount;

    private double score;
}
