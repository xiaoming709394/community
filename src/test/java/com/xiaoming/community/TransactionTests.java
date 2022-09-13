package com.xiaoming.community;

import com.xiaoming.community.service.AlphaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 事务测试类
 *
 * @author 赵明城
 * @date 2022/9/11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class TransactionTests {

    @Autowired
    private AlphaService alphaService;

    /**
     * 测试声明式事务
     */
    @Test
    public void testSave1() {
        Object obj = alphaService.testSave1();
        System.out.println(obj);
    }

    /**
     * 测试编程式事务
     */
    @Test
    public void testSave2() {
        Object obj = alphaService.testSave2();
        System.out.println(obj);
    }

}
