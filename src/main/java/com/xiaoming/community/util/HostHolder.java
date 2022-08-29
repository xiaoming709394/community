package com.xiaoming.community.util;

import com.xiaoming.community.entity.User;
import org.springframework.stereotype.Component;

/**
 * 持有用户信息，用于代替session对象
 *
 * @author 赵明城
 * @date 2022/8/29
 */
@Component
public class HostHolder {

    private ThreadLocal<User> users = new ThreadLocal<>();

    /**
     * 存储用户信息
     *
     * @param user
     */
    public void setUser(User user) {
        users.set(user);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public User getUser() {
        return users.get();
    }

    /**
     * 删除用于信息
     */
    public void clear() {
        users.remove();
    }

}
