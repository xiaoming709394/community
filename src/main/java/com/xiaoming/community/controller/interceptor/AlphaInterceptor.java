package com.xiaoming.community.controller.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器test类
 *
 * @author 赵明城
 * @date 2022/8/28
 */
@Slf4j
@Component
public class AlphaInterceptor implements HandlerInterceptor {

    /**
     * 在controller执行之前
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("preHandle：" + handler.toString());
        return true;
    }

    /**
     * 在controller之后执行
     *
     * @param request
     * @param response
     * @param handle
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handle, ModelAndView modelAndView) throws Exception {
        log.debug("postHandle：" + handle.toString());
    }

    /**
     * 在TemplateEngine之后执行
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.debug("afterCompletion：" + handler.toString());
    }

}
