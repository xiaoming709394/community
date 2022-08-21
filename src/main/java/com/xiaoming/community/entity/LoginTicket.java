package com.xiaoming.community.entity;

import lombok.Data;

import java.util.Date;

/**
 * 登录信息实体
 *
 * @author 赵明城
 * @date 2022/8/20
 */
@Data
public class LoginTicket {

    /**
     * id
     */
    private int id;

    /**
     * 用户id
     */
    private int userId;

    /**
     * session票据
     */
    private String ticket;

    /**
     * 状态（0-有效; 1-无效;）
     */
    private int status;

    /**
     *失效日期
     */
    private Date expired;

}
