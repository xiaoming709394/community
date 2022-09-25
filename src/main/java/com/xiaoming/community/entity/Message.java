package com.xiaoming.community.entity;


import lombok.Data;

import java.util.Date;

/**
 * 私信记录实体
 *
 * @author 赵明城
 * @date 2022/9/14
 */
@Data
public class Message {

    /**
     * id
     */
    public int id;
    /**
     * 发送者id
     */
    public int fromId;
    /**
     * 接收者id
     */
    public int toId;
    /**
     * 会话id
     */
    public String conversationId;
    /**
     * 发送内容
     */
    public String content;
    /**
     * 状态 0-未读;1-已读;2-删除;
     */
    public int status;
    /**
     * 创建时间
     */
    public Date createTime;

}
