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
}
