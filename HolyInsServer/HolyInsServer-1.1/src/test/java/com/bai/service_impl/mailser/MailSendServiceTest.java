package com.bai.service_impl.mailser;

import com.bai.config.AppConfig;
import com.bai.service_impl.mailsend_serviceImpl.MailSendServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.Properties;

public class MailSendServiceTest {
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    MailSendServiceImpl sendService = context.getBean("mailSendServiceImpl", MailSendServiceImpl.class);

    @Test
    public void sendPswValidationMailTest() {
        sendService.sendPswValidationMail("1748383809@qq.com", "王火炬", "746528");
    }

    @Test
    public void sendRegisterMailTest() {
        sendService.sendRegisterMail("1748383809@qq.com", "张文豪", "746625");
    }

    @Test
    public void PropertiesTest() {
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream("mail_config/mailConfig.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(properties.getProperty("host"));
        System.out.println(properties.getProperty("protocol"));
        System.out.println(properties.getProperty("username"));
        System.out.println(properties.getProperty("userpassword"));
        System.out.println(properties.getProperty("chatset"));
        System.out.println(properties.getProperty("mail.smtp.ssl.enable"));
    }
}
