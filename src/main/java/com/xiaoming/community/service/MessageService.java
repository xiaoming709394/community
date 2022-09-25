package com.xiaoming.community.service;

import com.xiaoming.community.dao.MessageMapper;
import com.xiaoming.community.entity.Message;
import com.xiaoming.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 私信记录service
 *
 * @author 赵明城
 * @date 2022/9/15
 */
@Service
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    /**
     * 查询当前用户的会话列表，针对每个会话只返回一条最新的私信
     *
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    public List<Message> selectConversations(int userId, int offset, int limit) {
        return messageMapper.selectConversations(userId, offset, limit);
    }

    /**
     * 查询当前用户的会话数量
     *
     * @param userId
     * @return
     */
    public int selectConversationCount(int userId) {
        return messageMapper.selectConversationCount(userId);
    }

    /**
     * 查询某个会话包括的私信列表
     *
     * @param conversationId
     * @param offset
     * @param limit
     * @return
     */
    public List<Message> selectLetters(String conversationId, int offset, int limit) {
        return messageMapper.selectLetters(conversationId, offset, limit);
    }

    /**
     * 查询某个会话所包含的私信数量
     *
     * @param conversationId
     * @return
     */
    public int selectLetterCount(String conversationId) {
        return messageMapper.selectLetterCount(conversationId);
    }

    /**
     * 查询未读私信的数量（根据是否存在conversationId，来获取总未读数量或者某一会话未读数量）
     *
     * @param userId
     * @param conversationId
     * @return
     */
    public int selectLetterUnreadCount(int userId, String conversationId) {
        return messageMapper.selectLetterUnreadCount(userId, conversationId);
    }

    /**
     * 新增私信消息
     *
     * @param message
     * @return
     */
    public int insertMessage(Message message) {
        return messageMapper.insertMessage(message);
    }

    /**
     * 修改消息的状态
     *
     * @param ids
     * @param status
     * @return
     */
    public int updateStatus(List<Integer> ids, int status) {
        return messageMapper.updateStatus(ids, status);
    }

}
