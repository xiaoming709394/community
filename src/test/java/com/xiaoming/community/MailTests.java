package com.xiaoming.community;

import com.xiaoming.community.util.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author 赵明城
 * @date 2022/8/16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTests {

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 发送文本邮件
     */
    @Test
    public void testTextMail() {
        mailClient.sendMail("1124446558@qq.com", "TEST", "Welcome.");
    }

    @Test
    public void testHtmlMail() {
        Context context = new Context();
        context.setVariable("username", "xiaoming");

        String content = templateEngine.process("/mail/demo", context);
        System.out.println(context);

        mailClient.sendMail("1124446558@qq.com", "HTML", content);
    }

}
