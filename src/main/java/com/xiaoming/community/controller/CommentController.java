package com.xiaoming.community.controller;

import com.xiaoming.community.entity.Comment;
import com.xiaoming.community.entity.User;
import com.xiaoming.community.service.CommentService;
import com.xiaoming.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

/**
 * 评论信息控制器
 *
 * @author 赵明城
 * @date 2022/9/12
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private HostHolder hostHolder;

    /**
     * 添加帖子的评论
     *
     * @param discussPostId
     * @param comment
     * @return
     */
    @RequestMapping(path = "/add/{discussPostId}", method = RequestMethod.POST)
    public String addComment(@PathVariable("discussPostId") String discussPostId, Comment comment) {
        User user = hostHolder.getUser();
        if (user == null) {
            throw new IllegalArgumentException("未登录账号");
        }
        comment.setUserId(user.getId());
        comment.setStatus(0);
        comment.setCreateTime(new Date());
        commentService.addComment(comment);

        return "redirect:/discuss/detail/" + discussPostId;
    }

}
