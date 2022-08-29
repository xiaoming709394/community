package com.xiaoming.community.controller.interceptor;

import com.xiaoming.community.entity.LoginTicket;
import com.xiaoming.community.entity.User;
import com.xiaoming.community.service.UserService;
import com.xiaoming.community.util.CookieUtil;
import com.xiaoming.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 登录凭证信息获取拦截器
 *
 * @author 赵明城
 * @date 2022/8/29
 */
@Component
public class LoginTicketInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    /**
     * 运行在controller前
     *
     * @param request
     * @param response
     * @param handle
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception {
        //从cookie中获取登录凭证
        String ticket = CookieUtil.getValue(request, "ticket");

        if (ticket != null) {
            //查询登录凭证信息
            LoginTicket loginTicket = userService.findLoginTicket(ticket);
            //判断凭证是否有效
            if (loginTicket != null && loginTicket.getStatus() == 0 && loginTicket.getExpired().after(new Date())) {
                //根据凭证信息获取用户信息
                User user = userService.findUserById(loginTicket.getUserId());
                //在本次请求中持有用户信息
                hostHolder.setUser(user);
            }
        }

        return true;
    }

    /**
     * 运行在controller之后
     *
     * @param request
     * @param reponse
     * @param handler
     * @param modelAndView
     * @return
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse reponse, Object handler, ModelAndView modelAndView) throws Exception {
        //获取用户信息
        User user = hostHolder.getUser();
        if (user != null && modelAndView != null) {
            //生成模板属性
            modelAndView.addObject("loginUser", user);
        }
    }

    /**
     * 运行在TemplateEngine后
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清楚用户信息
        hostHolder.clear();
    }

}
