package com.xiaoming.community.service;

import com.xiaoming.community.dao.UserMapper;
import com.xiaoming.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户信息Service
 *
 * @author 赵明城
 * @date 2022/8/6
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 通过id获取用户信息
     *
     * @param id
     * @return
     */
    public User findUserById(int id) {
        return userMapper.selectById(id);
    }

}
