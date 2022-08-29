package com.xiaoming.community.controller;

import com.xiaoming.community.util.HostHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用户信息controller
 *
 * @author 赵明城
 * @date 2022/8/29
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * 头像报错路径
     */
    @Value("${community.path.upload}")
    private String uploadPath;

    /**
     * 域名
     */
    @Value("${community.path.domain}")
    private String domain;

    /**
     * 根目录
     */
    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private HostHolder hostHolder;

    /**
     * 账号设置页面
     *
     * @return
     */
    @RequestMapping(path = "/setting", method = RequestMethod.GET)
    public String getSettingPage() {
        return "site/setting";
    }

}
