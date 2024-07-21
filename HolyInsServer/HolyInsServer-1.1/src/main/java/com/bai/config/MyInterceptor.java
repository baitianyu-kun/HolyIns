package com.bai.config;

import com.bai.session.MySessionContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

public class MyInterceptor extends HandlerInterceptorAdapter {
    //处理前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("[MYLOG] 拦截器处理前");
        MySessionContext sessionContext = MySessionContext.getInstance();
        HashMap<String, HttpSession> stringHttpSessionHashMap = sessionContext.returnAllSession();
        for (String key : stringHttpSessionHashMap.keySet()) {
            //System.err.println("key=" + key);
            HttpSession session = sessionContext.getSession(key);
            if ((String) session.getAttribute("password")!=null){
                return true;
            }
        }
        sessionContext.getAllSession();
        System.out.println("[MYLOG] 未登录用户已被拦截 请求地址为："+request.getRequestURI());
        return false;
    }
}
