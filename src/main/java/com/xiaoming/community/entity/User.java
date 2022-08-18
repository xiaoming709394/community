package com.xiaoming.community.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户信息实体
 *
 * @author 赵明城
 * @date 2022/8/6
 */
@Data
public class User {

    /**
     * id
     */
    private int id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码自动补全后缀
     */
    private String salt;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户类型
     */
    private int type;

    /**
     * 账号状态
     */
    private int status;

    /**
     * 激活码
     */
    private String activationCode;

    /**
     * 头像路径
     */
    private String headerUrl;

    /**
     * 创建时间
     */
    private Date createTime;
}
