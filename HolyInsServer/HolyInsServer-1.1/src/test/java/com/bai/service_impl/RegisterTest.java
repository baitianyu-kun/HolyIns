package com.bai.service_impl;

import com.bai.config.AppConfig;
import com.bai.pojo.LoginInfo;
import com.bai.pojo.UserInfo;
import com.bai.service.content_service.ContentService;
import com.bai.service_impl.content_serviceImpl.ContentServiceImpl;
import com.bai.service_impl.register_serviceImpl.RegisterServiceImpl;

import com.bai.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class RegisterTest {
    @Test
    public void RegisterTest() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        RegisterServiceImpl registerServiceImpl = context.getBean("registerServiceImpl", RegisterServiceImpl.class);
        //int register = registerServiceImpl.Register(new UserInfo("new_name","new_Ac","new_em","new_head","new_size","new_sex",DateUtils.getLocalDate_AND_Time()), new LoginInfo("new_account", "new_email", "new_pwd", "new_name"));
        //System.out.println("注册结果"+register);
    }

    @Test
    public void isAccountEmailExist() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        RegisterServiceImpl registerServiceImpl = context.getBean("registerServiceImpl", RegisterServiceImpl.class);
        Integer i = registerServiceImpl.IsAccountExist("new_account");
        Integer i1 = registerServiceImpl.IsE_mailExist("new_email");
    }
}
