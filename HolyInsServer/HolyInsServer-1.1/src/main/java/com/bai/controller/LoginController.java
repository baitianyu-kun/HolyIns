package com.bai.controller;

import com.alibaba.fastjson.JSON;
import com.bai.pojo.LoginInfo;
import com.bai.service.login_service.LoginService;
import com.bai.session.MySessionContext;
import com.bai.state.ActivityStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequestMapping("/login")
@RestController
public class LoginController {

    private LoginService loginService;

    @Autowired
    @Qualifier("loginServiceImpl")
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public String login(@RequestParam("login_info") String JSON_loginInfo, HttpServletRequest request, HttpServletResponse response) {
        LoginInfo loginInfo = JSON.parseObject(JSON_loginInfo, LoginInfo.class);
        //这里的sessionListener一定要去web.xml中注册才行
        //如果取得的session不为空的话,就用那里面的信息来填充loginInfo
        if (loginInfo.getSession_id() != null) {
            System.err.println("[MYLOG] session_id:" + loginInfo.getSession_id());
            MySessionContext loginSessionContext = MySessionContext.getInstance();
            HttpSession session = loginSessionContext.getSession(loginInfo.getSession_id());
            //如果session_id没有对应上任何一个session的话
            if (session == null) {
                System.err.println("[MYLOG] session_is_null");
                return ActivityStatus.STRING_LOGIN_FAILED;
            }
            loginInfo.setAccount((String) session.getAttribute("account"));
            loginInfo.setE_mail((String) session.getAttribute("e_mail"));
            loginInfo.setPassword((String) session.getAttribute("password"));
            System.err.println("[MYLOG] NowUser:"+loginInfo.toString());
            if (loginService.Login(loginInfo) == ActivityStatus.LOGIN_SUCCESS) {
                return ActivityStatus.STRING_LOGIN_SUCCESS;
            } else {
                return ActivityStatus.STRING_LOGIN_FAILED;
            }
        } else {
            if (loginService.Login(loginInfo) == ActivityStatus.LOGIN_SUCCESS) {
                //创建session
                HttpSession session = request.getSession();
                //往session中储存用户信息
                session.setAttribute("account", loginInfo.getAccount());
                session.setAttribute("password", loginInfo.getPassword());
                session.setAttribute("e_mail", loginInfo.getE_mail());
                //设置session的有效期
                session.setMaxInactiveInterval(Integer.MAX_VALUE);

                if (session.isNew()) {
                    System.err.println("[MYLOG] session创建成功 " + session.getId());
                } else {
                    System.err.println("[MYLOG] session已经存在，可以直接登录" + session.getId());
                }
                return session.getId();
            } else {
                return ActivityStatus.STRING_LOGIN_FAILED;
            }
        }
    }

}
