package com.xiaoming.community.service;

import com.xiaoming.community.dao.DiscussPostMapper;
import com.xiaoming.community.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 帖子信息Service
 *
 * @author 赵明城
 * @date 2022/8/7
 */
@Service
public class DiscussPostService {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    /**
     * 获取帖子信息
     *
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit) {
        return discussPostMapper.selectDiscussPosts(userId, offset, limit);
    }

    /**
     * 获取帖子的总数量
     *
     * @param userId
     * @return
     */
    public int findDiscussPostRows(int userId) {
        return discussPostMapper.selectDiscussPostRows(userId);
    }
}
