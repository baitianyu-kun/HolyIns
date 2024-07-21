package com.bai.config;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebConfig implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        //加载web应用
        AnnotationConfigWebApplicationContext context=new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class);
        //注册dispatcherServlet
        DispatcherServlet dispatcherServlet=new DispatcherServlet(context);
        ServletRegistration.Dynamic registration=servletContext.addServlet("app",dispatcherServlet);
        //启动级别为1
        registration.setLoadOnStartup(1);
        //servlet映射器
        registration.addMapping("/");
    }

}
