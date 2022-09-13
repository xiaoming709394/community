package com.xiaoming.community.dao;

import com.xiaoming.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 评论信息Mapper接口
 *
 * @author 赵明城
 * @date 2022/9/12
 */
@Mapper
public interface CommentMapper {

    /**
     * 获取评论列表
     *
     * @param entityType
     * @param entityId
     * @param offset
     * @param limit
     * @return
     */
    List<Comment> selectCommentsByEntity(int entityType, int entityId, int offset, int limit);

    /**
     * 获取评论总数
     *
     * @param entityType
     * @param entityId
     * @return
     */
    int selectCountByEntity(int entityType, int entityId);

    /**
     * 添加评论信息
     *
     * @param comment
     * @return
     */
    int insertComment(Comment comment);

}
