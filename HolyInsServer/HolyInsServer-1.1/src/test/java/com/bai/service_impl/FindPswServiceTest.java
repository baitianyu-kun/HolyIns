package com.bai.service_impl;

import com.bai.config.AppConfig;
import com.bai.pojo.LoginInfo;
import com.bai.service_impl.findpsw_serviceImpl.FindPswServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class FindPswServiceTest {
    @Test
    public void FindPswServiceTest()
    {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        FindPswServiceImpl findPswServiceImpl = context.getBean("findPswServiceImpl", FindPswServiceImpl.class);
        System.out.println("更改密码状态为："+findPswServiceImpl.FindPsw(new LoginInfo("174","123333")));
        System.out.println("returnUserName="+findPswServiceImpl.ReturnUserName("174"));
    }
}
