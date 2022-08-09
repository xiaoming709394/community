package com.xiaoming.community;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * 日志测试类
 *
 * @author 赵明城
 * @date 2022/8/9
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
@Slf4j
public class LoggerTests {

    //private static final Logger logger = LoggerFactory.getLogger(LoggerTests.class);
    //
    //@Test
    //public void testLogger() {
    //    System.out.println(logger.getName());
    //
    //    logger.debug("debug log");
    //    logger.info("info log");
    //    logger.warn("warn log");
    //    logger.error("error log");
    //}

    //可以用这种注解的形式来写日志，也可以用上面实例化的形式
    @Test
    public void testLogger() {
        System.out.println(log.getName());

        log.debug("debug log");
        log.info("info log");
        log.warn("warn log");
        log.error("error log");
    }

}
