package com.xiaoming.community.controller;

import com.xiaoming.community.util.CommunityConstant;
import com.xiaoming.community.util.CommunityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 测试
 *
 * @author 赵明城
 * @date 2022/8/7
 */
@Controller
@RequestMapping("/alpha")
public class AlphaController {

    /**
     * 测试前端模板
     *
     * @param model
     * @return
     */
    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool(Model model) {
        model.addAttribute("name", "北京大学");
        model.addAttribute("age", 150);
        return "/demo/view";
    }

    /**
     * 生成cookie测试
     *
     * @param response
     * @return
     */
    @RequestMapping(path = "/cookie/set", method = RequestMethod.GET)
    @ResponseBody
    public String setCookie(HttpServletResponse response) {
        //创建cookie
        Cookie cookie = new Cookie("code", CommunityUtil.generateUUID());
        //设置cookie的生效范围
        cookie.setPath("/community/alpha");
        //设置cookie的生存时间(10分钟)
        cookie.setMaxAge(60 * 10);
        //发送cookie
        response.addCookie(cookie);

        return "set cookie";
    }

    /**
     * 获取cookie测试
     *
     * @param code
     * @return
     */
    @RequestMapping(path = "/cookie/get", method = RequestMethod.GET)
    @ResponseBody
    public String getCookie(@CookieValue("code") String code) {
        System.out.println(code);
        return "get cookie";
    }

    /**
     * 生成session测试
     *
     * @param seesion
     * @return
     */
    @RequestMapping(path = "/session/set", method = RequestMethod.GET)
    @ResponseBody
    public String getSession(HttpSession seesion) {
        seesion.setAttribute("id", 1);
        seesion.setAttribute("name", "Test");
        return "set session";
    }

    /**
     * 获取session
     *
     * @param session
     * @return
     */
    @RequestMapping(path = "/session/get", method = RequestMethod.GET)
    @ResponseBody
    public String getSeesion(HttpSession session) {
        System.out.println(session.getAttribute("id"));
        System.out.println(session.getAttribute("name"));
        return "get session";
    }
}
