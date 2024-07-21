package com.bai.config;

import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;

@Configuration
public class AppConfigMail {
    @Bean
    public JavaMailSenderImpl mailSender() throws GeneralSecurityException, IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("mail_config/mailConfig.properties"));
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        //配置普通参数
        javaMailSender.setHost(properties.getProperty("host"));
        javaMailSender.setProtocol(properties.getProperty("protocol"));
        javaMailSender.setUsername(properties.getProperty("useraccount"));
        javaMailSender.setPassword(properties.getProperty("password"));
        javaMailSender.setDefaultEncoding(properties.getProperty("defaultEncoding"));
        //qq邮箱需要写这个，就是ssl证书
        MailSSLSocketFactory mailSSLSocketFactory = new MailSSLSocketFactory();
        mailSSLSocketFactory.setTrustAllHosts(true);
        //配置其他参数
        Properties pro = new Properties();
        pro.put("mail.smtp.auth", properties.getProperty("mail.smtp.auth"));
        pro.put("mail.smtp.ssl.enable", properties.getProperty("mail.smtp.ssl.enable"));
        //判断是否需要产生ssl证书
        if (properties.getProperty("mail.smtp.ssl.socketFactory").equals("true")) {
            pro.put("mail.smtp.ssl.socketFactory", mailSSLSocketFactory);
        }
        javaMailSender.setJavaMailProperties(pro);//这个就相当于资料下的那个xml文件，它可以再properties里再配置一些参数
        //是否打开session的debug模式，即是否在控制台输出发送邮件的debug信息
        if (properties.getProperty("sessionEnableDebug").equals("true")) {
            javaMailSender.getSession().setDebug(true);
        }
        return javaMailSender;
    }

    @Bean
    public FreeMarkerConfigurationFactoryBean freeMarkerConfiguration() {
        FreeMarkerConfigurationFactoryBean factoryBean = new FreeMarkerConfigurationFactoryBean();
        //配置加载template的地址，classpath:/ 这个地址需要文件在resources下
        factoryBean.setTemplateLoaderPath("classpath:/mail_template/");
        factoryBean.setDefaultEncoding("utf-8");
        return factoryBean;
    }
}
