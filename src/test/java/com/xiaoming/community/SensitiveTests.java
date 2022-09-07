package com.xiaoming.community;

import com.xiaoming.community.util.SensitiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 敏感词测试类
 *
 * @author 赵明城
 * @date 2022/9/7
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class SensitiveTests {

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void testSensitiveFilter() {
        String text = "这里可以开票，可以赌博";
        text = sensitiveFilter.filter(text);
        System.out.println(text);

        text = "这里可以☆开☆票☆，可以☆赌☆博☆";
        text = sensitiveFilter.filter(text);
        System.out.println(text);

    }

}
