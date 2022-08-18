package com.xiaoming.community.dao;

import com.xiaoming.community.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息Mapper接口
 *
 * @author 赵明城
 * @date 2022/8/6
 */
@Mapper
public interface UserMapper {

    /**
     * 通过id获取用户信息
     *
     * @param id
     * @return
     */
    User selectById(int id);

    /**
     * 通过用户名称获取用户信息
     *
     * @param username
     * @return
     */
    User selectByName(String username);

    /**
     * 通过邮箱获取用户信息
     *
     * @param email
     * @return
     */
    User selectByEmail(String email);

    /**
     * 插入用户信息
     *
     * @param user
     * @return
     */
    int insertUser(User user);

    /**
     * 更新用户状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatus(int id, int status);

    /**
     * 更新用户头像
     *
     * @param id
     * @param headerUrl
     * @return
     */
    int updateHeader(int id, String headerUrl);

    /**
     * 更新用户密码
     *
     * @param id
     * @param password
     * @return
     */
    int updatePassword(int id, String password);

}
