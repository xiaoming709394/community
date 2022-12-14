package com.xiaoming.community.service;

import com.google.code.kaptcha.Producer;
import com.xiaoming.community.dao.LoginTicketMapper;
import com.xiaoming.community.dao.UserMapper;
import com.xiaoming.community.entity.LoginTicket;
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
    private LoginTicketMapper loginTicketMapper;

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
        //可以用注解把自动生成的id赋值到对应的entity对象，不用多查询一次
        //User userByName = userMapper.selectByName(user.getUsername());
        // http://localhost:8080/community/activation/101/code
        String url = domain + contextPath + "/activation/" + user.getId() + "/" + user.getActivationCode();
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

    /**
     * 登录
     *
     * @param username
     * @param password
     * @param expiredSeconds
     * @return
     */
    public Map<String, Object> login(String username, String password, int expiredSeconds) {
        Map<String, Object> map = new HashMap<>();

        //空值处理
        if (StringUtils.isBlank(username)) {
            map.put("usernameMsg", "账号不能为空");
        }
        if (StringUtils.isBlank(password)) {
            map.put("passwordMsg", "密码不能为空");
        }

        //验证账号是否存在
        User user = userMapper.selectByName(username);
        if (user == null) {
            map.put("usernameMsg", "该账号不存在");
            return map;
        }

        //验证转态
        if (user.getStatus() == 0) {
            map.put("usernameMsg", "该账号未激活");
            return map;
        }

        //验证密码
        password = CommunityUtil.md5(password + user.getSalt());
        if (!user.getPassword().equals(password)) {
            map.put("passwordMsg", "密码不正确");
            return map;
        }

        //生成登录凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(CommunityUtil.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));
        loginTicketMapper.insertLoginTicket(loginTicket);

        map.put("ticket", loginTicket.getTicket());
        return map;
    }

    /**
     * 注销登录
     *
     * @param ticket
     */
    public void logout(String ticket) {
        loginTicketMapper.updateStatus(ticket, 1);
    }

    /**
     * 通过凭证号获取登录信息
     *
     * @param ticket
     * @return
     */
    public LoginTicket findLoginTicket(String ticket) {
        return loginTicketMapper.selectByTicket(ticket);
    }

    /**
     * 更新用户头像
     *
     * @param id
     * @param headerUrl
     * @return
     */
    public int updateHeader(int id, String headerUrl) {
        return userMapper.updateHeader(id, headerUrl);
    }

    /**
     * 更改密码
     *
     * @param id
     * @param oldPassword
     * @param newPassword
     * @return
     */
    public Map<String, Object> updatePassword(int id, String oldPassword, String newPassword) {
        Map<String, Object> map = new HashMap<>();
        //判断密码是否为空
        if (StringUtils.isBlank(oldPassword)) {
            map.put("oldPasswordMsg", "请输入原密码");
            return map;
        }
        if (StringUtils.isBlank(newPassword)) {
            map.put("newPasswordMsg", "请输入新密码");
            return map;
        }

        //获取用户信息
        User user = userMapper.selectById(id);
        //判断原密码是否一致
        oldPassword = CommunityUtil.md5(oldPassword + user.getSalt());
        if (!user.getPassword().equals(oldPassword)) {
            map.put("oldPasswordMsg", "请输入正确的原密码");
            return map;
        }

        //更新密码
        newPassword = CommunityUtil.md5(newPassword + user.getSalt());
        userMapper.updatePassword(id, newPassword);
        return null;
    }

    /**
     * 通过用户名称获取用户信息
     *
     * @param userName
     * @return
     */
    public User findUserByName(String userName) {
        return userMapper.selectByName(userName);
    }

}
