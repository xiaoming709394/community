package com.xiaoming.community.controller.advice;

import com.xiaoming.community.util.CommunityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 统一处理异常类
 * annotations = Controller.class 代表只扫描带有Controller注解的bean
 *
 * @author 赵明城
 * @date 2022/9/25
 */
@Slf4j
@ControllerAdvice(annotations = Controller.class)
public class ExceptionAdvice {

    /**
     * 统一捕获异常方法
     * <p>
     * {Exception.class}为所有异常的父类，等于捕获所有异常
     *
     * @param e
     * @param request
     * @param response
     */
    @ExceptionHandler({Exception.class})
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //记录日志
        log.error("服务器发生异常" + e.getMessage());
        for (StackTraceElement element : e.getStackTrace()) {
            log.error(element.toString());
        }

        //返回错误提示
        String xRequestedWith = request.getHeader("x-requested-with");
        if ("XMLHttpRequest".equals(xRequestedWith)) { //异步请求
            //application/plain返回的数据类型为string，可以为json字符串
            response.setContentType("application/plain;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(CommunityUtil.getJsonString(1, "服务器报错"));
        } else { //普通请求
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }

}
