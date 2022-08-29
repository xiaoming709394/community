package com.xiaoming.community.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * cookie共用类
 *
 * @author 赵明城
 * @date 2022/8/29
 */
public class CookieUtil {

    /**
     * 获取cooike value值
     *
     * @param request
     * @param name
     * @return
     */
    public static String getValue(HttpServletRequest request, String name) {
        if (request == null || name == null) {
            throw new IllegalArgumentException("参数为空");
        }

        //获取cookie数组
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
