package com.bai.service_impl;

import com.bai.config.AppConfig;
import com.bai.service_impl.person_info_serviceImpl.PersonInfoServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PersonInfoServiceTest {
    @Test
    public void forwardTest(){
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        PersonInfoServiceImpl personInfoServiceImpl = context.getBean("personInfoServiceImpl", PersonInfoServiceImpl.class);
        personInfoServiceImpl.mapperTest(1);
    }
}
