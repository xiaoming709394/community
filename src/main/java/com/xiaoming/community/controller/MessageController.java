package com.xiaoming.community.controller;

import com.xiaoming.community.entity.Message;
import com.xiaoming.community.entity.Page;
import com.xiaoming.community.entity.User;
import com.xiaoming.community.service.MessageService;
import com.xiaoming.community.service.UserService;
import com.xiaoming.community.util.CommunityUtil;
import com.xiaoming.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 私信消息controller
 *
 * @author 赵明城
 * @date 2022/9/15
 */
@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    /**
     * 私信列表
     *
     * @param model
     * @param page
     * @return
     */
    @RequestMapping(path = "/letter/list", method = RequestMethod.GET)
    public String getLetterList(Model model, Page page) {
        User user = hostHolder.getUser();
        //分页信息
        page.setLimit(5);
        page.setPath("/letter/list");
        page.setRows(messageService.selectConversationCount(user.getId()));

        //会话列表
        List<Message> conversationList = messageService.selectConversations(
                user.getId(), page.getOffset(), page.getLimit());
        List<Map<String, Object>> conversations = new ArrayList<>();
        for (Message message : conversationList) {
            Map<String, Object> map = new HashMap<>();
            map.put("conversation", message);
            map.put("letterCount", messageService.selectLetterCount(message.getConversationId()));
            map.put("unreadCount", messageService.selectLetterUnreadCount(user.getId(), message.getConversationId()));
            int tagertId = user.getId() == message.getFromId() ? message.getToId() : message.getFromId();
            map.put("target", userService.findUserById(tagertId));

            conversations.add(map);
        }
        model.addAttribute("conversations", conversations);

        //查询未读消息数量
        int letterUnreadCount = messageService.selectLetterUnreadCount(user.getId(), null);
        model.addAttribute("letterUnreadCount", letterUnreadCount);

        return "/site/letter";
    }

    /**
     * 获取会话明细
     *
     * @param conversationId
     * @param page
     * @param model
     * @return
     */
    @RequestMapping(path = "/letter/detail/{conversationId}", method = RequestMethod.GET)
    public String getLetterDetail(@PathVariable("conversationId") String conversationId, Page page, Model model) {
        //分页查询
        page.setLimit(5);
        page.setPath("/letter/detail/" + conversationId);
        page.setRows(messageService.selectLetterCount(conversationId));

        //获取私信列表
        List<Message> letterList = messageService.selectLetters(conversationId, page.getOffset(), page.getLimit());
        List<Map<String, Object>> letters = new ArrayList<>();
        if (letterList != null) {
            for (Message message : letterList) {
                Map<String, Object> map = new HashMap<>();
                map.put("letter", message);
                map.put("fromUser", userService.findUserById(message.getFromId()));
                letters.add(map);
            }
        }
        model.addAttribute("letters", letters);

        //私信目标
        model.addAttribute("target", getLetterTarget(conversationId));

        //设置已读
        List<Integer> ids = getLetterIds(letterList);
        if (!ids.isEmpty()) {
            messageService.updateStatus(ids, 1);
        }

        return "/site/letter-detail";
    }

    /**
     * 保存私信
     *
     * @param toName
     * @param content
     * @return
     */
    @RequestMapping(path = "/letter/send", method = RequestMethod.POST)
    @ResponseBody
    public String sendLetter(String toName, String content) {
        //获取接收人信息
        User target = userService.findUserByName(toName);
        if (target == null) {
            return CommunityUtil.getJsonString(1, "目标用户不存在");
        }

        //私信列表
        Message message = new Message();
        message.setFromId(hostHolder.getUser().getId());
        message.setToId(target.getId());
        if (message.getFromId() < message.getToId()) {
            message.setConversationId(message.getFromId() + "_" + message.getToId());
        } else {
            message.setConversationId(message.getToId() + "_" + message.getFromId());
        }
        message.setContent(content);
        message.setCreateTime(new Date());
        messageService.insertMessage(message);

        return CommunityUtil.getJsonString(0);
    }

    /**
     * 获取目标用户信息
     *
     * @param conversationId
     * @return
     */
    private User getLetterTarget(String conversationId) {
        String[] ids = conversationId.split("_");
        int id0 = Integer.parseInt(ids[0]);
        int id1 = Integer.parseInt(ids[1]);

        if (hostHolder.getUser().getId() == id0) {
            return userService.findUserById(id1);
        } else {
            return userService.findUserById(id0);
        }
    }

    /**
     * 获取未读消息的id列表
     *
     * @param list
     * @return
     */
    private List<Integer> getLetterIds(List<Message> list) {
        List<Integer> ids = new ArrayList<>();

        for (Message message : list) {
            if (hostHolder.getUser().getId() == message.getToId() && message.getStatus() == 0) {
                ids.add(message.getId());
            }
        }

        return ids;
    }

}
