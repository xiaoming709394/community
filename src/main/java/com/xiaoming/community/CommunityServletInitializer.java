package com.xiaoming.community;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author 赵明城
 * @date 2022/11/12
 * Tomcat运行初始化类
 */
public class CommunityServletInitializer extends SpringBootServletInitializer {

    /**
     * 初始化
     *
     * @param builder
     * @return
     */
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CommunityApplication.class);
    }

}
