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

    private int id;

    private String username;

    private String password;

    private String salt;

    private String email;

    private int type;

    private int status;

    private String activation_code;

    private String headerUrl;

    private Date createTime;
}
