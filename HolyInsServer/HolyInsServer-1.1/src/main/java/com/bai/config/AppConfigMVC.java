package com.bai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
@ComponentScan("com.bai.controller")
public class AppConfigMVC implements WebMvcConfigurer {
    //注册视图解析器
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(JstlView.class);
        return viewResolver;
    }
    //文件解析器
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        commonsMultipartResolver.setMaxInMemorySize(10240);
        commonsMultipartResolver.setMaxUploadSize(-1);
        return commonsMultipartResolver;
    }
    //统一解决乱码，配置SpringMVC的message converter s
    /**
     * Configure the {@link HttpMessageConverter HttpMessageConverters} to use for reading or writing
     * to the body of the request or response. If no converters are added, a
     * default list of converters is registered.
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        StringHttpMessageConverter shm = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converters.add(shm);
    }

        //将自定义拦截器注册为bean
    @Bean
    public MyInterceptor initInterceptor() {
        return new MyInterceptor();
    }

    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //除登录注册与找回密码外,请求全部拦截
        registry.addInterceptor(initInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login/login")
                .excludePathPatterns("/register/register")
                .excludePathPatterns("/register/sendValidationCode")
                .excludePathPatterns("/register/validateCode")
                .excludePathPatterns("/findpsw/sendValidationCode")
                .excludePathPatterns("/findpsw/validateCode")
                .excludePathPatterns("/findpsw/changePsw");
    }

}
