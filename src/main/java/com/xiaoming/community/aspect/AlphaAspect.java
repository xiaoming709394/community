package com.xiaoming.community.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * AOP测试类
 *
 * @author 赵明城
 * @date 2022/9/26
 */
//@Component
//@Aspect
public class AlphaAspect {

    /**
     * 定义一个切点，目标方法
     * <p>
     * 第一个*号：表示返回类型，*号表示所有的类型;
     * 包名：表示需要拦截的包名，后面的两个句点表示当前包和当前包的所有子包，
     * com.xiaoming.community.service包、子孙包下所有类的方法，*号表示所有的类;
     * *(…):最后这个星号表示方法名，*号表示所有的方法，后面括弧里面表示方法的参数，两个句点表示任何参数。
     */
    @Pointcut("execution(* com.xiaoming.community.service.*.*(..))")
    public void pointcut() {

    }

    /**
     * 目标方法前植入
     */
    @Before("pointcut()")
    public void before() {
        System.out.println("before");
    }

    /**
     * 目标方法后植入
     */
    @After("pointcut()")
    public void after() {
        System.out.println("after");
    }

    /**
     * return之后植入
     */
    @AfterReturning("pointcut()")
    public void afterRetuning() {
        System.out.println("afterRetuning");
    }

    /**
     * 抛异常之后植入
     */
    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        System.out.println("afterThrowing");
    }

    /**
     * 环绕，参数ProceedingJoinPoint连接点，植入的部位
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around before");
        Object obj = joinPoint.proceed();
        System.out.println("around after");
        return obj;
    }

}
