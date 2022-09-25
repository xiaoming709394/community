package com.xiaoming.community.dao;

import com.xiaoming.community.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 私信记录Mapper接口
 *
 * @author 赵明城
 * @date 2022/9/14
 */
@Mapper
public interface MessageMapper {

    /**
     * 查询当前用户的会话列表，针对每个会话只返回一条最新的私信
     *
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    List<Message> selectConversations(int userId, int offset, int limit);

    /**
     * 查询当前用户的会话数量
     *
     * @param userId
     * @return
     */
    int selectConversationCount(int userId);

    /**
     * 查询某个会话包括的私信列表
     *
     * @param conversationId
     * @param offset
     * @param limit
     * @return
     */
    List<Message> selectLetters(String conversationId, int offset, int limit);

    /**
     * 查询某个会话所包含的私信数量
     *
     * @param ConversationId
     * @return
     */
    int selectLetterCount(String ConversationId);

    /**
     * 查询未读私信的数量
     *
     * @param userId
     * @param conversationId
     * @return
     */
    int selectLetterUnreadCount(int userId, String conversationId);

    /**
     * 新增私信消息
     *
     * @param message
     * @return
     */
    int insertMessage(Message message);

    /**
     * 修改消息的状态
     *
     * @param ids
     * @param status
     * @return
     */
    int updateStatus(List<Integer> ids, int status);

}
