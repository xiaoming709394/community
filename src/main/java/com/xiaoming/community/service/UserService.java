package com.xiaoming.community.service;

import com.xiaoming.community.dao.UserMapper;
import com.xiaoming.community.entity.User;
import com.xiaoming.community.util.CommunityConstant;
import com.xiaoming.community.util.CommunityUtil;
import com.xiaoming.community.util.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 用户信息Service
 *
 * @author 赵明城
 * @date 2022/8/6
 */
@Service
public class UserService implements CommunityConstant {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    /**
     * 通过id获取用户信息
     *
     * @param id
     * @return
     */
    public User findUserById(int id) {
        return userMapper.selectById(id);
    }

    /**
     * 注册
     *
     * @param user
     * @return
     */
    public Map<String, Object> register(User user) {
        Map<String, Object> map = new HashMap<>();

        //空值处理
        if (user == null) {
            throw new IllegalArgumentException("参数不能为空！");
        }
        if (StringUtils.isBlank(user.getUsername())) {
            map.put("username", "账号不能为空");
        }
        if (StringUtils.isBlank(user.getPassword())) {
            map.put("password", "密码不能为空");
        }
        if (StringUtils.isBlank(user.getEmail())) {
            map.put("email", "邮箱不能为空");
        }

        //验证账号
        User fUser = userMapper.selectByName(user.getUsername());
        if (fUser != null) {
            map.put("usernameMsg", "该账号已存在");
            return map;
        }

        //验证邮箱
        fUser = userMapper.selectByEmail(user.getEmail());
        if (fUser != null) {
            map.put("emailMsg", "该邮箱已被注册");
            return map;
        }

        //添加注册用户
        user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
        user.setPassword(CommunityUtil.md5(user.getPassword() + user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(CommunityUtil.generateUUID());
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setCreateTime(new Date());
        userMapper.insertUser(user);

        //发送激活邮件
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        //由于没能返回id,因此多查询一次
        User userByName = userMapper.selectByName(user.getUsername());
        // http://localhost:8080/community/activation/101/code
        String url = domain + contextPath + "/activation/" + userByName.getId() + "/" + user.getActivationCode();
        context.setVariable("url", url);
        String content = templateEngine.process("/mail/activation", context);
        mailClient.sendMail(user.getEmail(), "激活账号", content);

        return map;
    }

    /**
     * 用户激活
     *
     * @param userId
     * @param code
     * @return
     */
    public int activation(int userId, String code) {
        User user = userMapper.selectById(userId);
        if (user.getStatus() == 1) { //重复激活
            return ACTIVATION_REPEAT;
        } else if (user.getStatus() == 0) { //激活成功
            userMapper.updateStatus(userId, 1);
            return ACTIVATION_SUCCESS;
        } else { //激活失败
            return ACTIVATION_FAILURE;
        }
    }

}
