package com.bai.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/*
 * AOP切面打印全局日志
 **/
@Aspect
@Component
public class AnnotationPointCut {

    @Pointcut("execution(* com.bai.service_impl.*.*.*(..))")
    private void anyMethod() {
    }

    @Before("anyMethod()")
    public void before(JoinPoint joinPoint) {
        System.out.println("[MYLOG] =======前置日志输出=======");
        System.out.println(
                " 服务："+joinPoint.getTarget()
                +"\n 执行了：" + joinPoint.getSignature().getName()
                + "\n 参数为：" + Arrays.toString(joinPoint.getArgs()));
    }

    @After("anyMethod()")
    public void after() {
        System.out.println("[MYLOG] =======后置日志输出=======");
    }

}
