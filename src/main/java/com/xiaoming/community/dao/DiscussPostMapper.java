package com.xiaoming.community.dao;

import com.xiaoming.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 帖子信息Mapper接口
 *
 * @author 赵明城
 * @date 2022/8/6
 */
@Mapper
public interface DiscussPostMapper {

    /**
     * 查看帖子信息
     *
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    /**
     * 帖子总数
     *
     * @param userId
     * @return
     */
    int selectDiscussPostRows(@Param("userId") int userId);
}
