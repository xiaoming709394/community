package com.xiaoming.community.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 发送邮件公共类
 *
 * @author 赵明城
 * @date 2022/8/16
 */
@Slf4j
@Component
public class MailClient {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送邮件方法
     *
     * @param to
     * @param subject
     * @param content
     */
    public void sendMail(String to, String subject, String content) {
        try {
            MimeMessage massge = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(massge);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(helper.getMimeMessage());
        } catch (MessagingException e) {
            log.error("发送邮件失败" + e.getMessage());
        }
    }
}
