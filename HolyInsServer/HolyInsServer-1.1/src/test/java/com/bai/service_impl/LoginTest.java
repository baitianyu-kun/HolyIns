package com.bai.service_impl;

import com.bai.config.AppConfig;
import com.bai.pojo.LoginInfo;
import com.bai.service_impl.login_serviceImpl.LoginServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LoginTest {
    @Test
    public void LoginTest()
    {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        LoginServiceImpl loginServiceImpl = context.getBean("loginServiceImpl", LoginServiceImpl.class);
       // System.out.println("登录状态"+loginServiceImpl.Login(new LoginInfo("159",null,"4506",null)));
    }
}
