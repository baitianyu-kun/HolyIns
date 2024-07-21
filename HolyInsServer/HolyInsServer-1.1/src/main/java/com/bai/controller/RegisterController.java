package com.bai.controller;

import com.alibaba.fastjson.JSON;
import com.bai.pojo.LoginInfo;
import com.bai.pojo.UserInfo;
import com.bai.service.mailsend_service.MailSendService;
import com.bai.service.register_service.RegisterService;
import com.bai.service.validatecode_service.Validatecode_Service;
import com.bai.state.ActivityStatus;
import com.bai.state.StorageState;
import com.bai.utils.DateUtils;
import com.bai.utils.IntegerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

@RequestMapping("/register")
@RestController
public class RegisterController {

    private String ValidationCode = ActivityStatus.STRING_VALIDATION_CODE_DATED;
    private String UserE_Mail = ActivityStatus.STRING_DEFAULT_E_MAIL;

    private RegisterService registerService;
    private Validatecode_Service validatecode_service;

    @Autowired
    @Qualifier("registerServiceImpl")
    public void setRegisterService(RegisterService registerService) {
        this.registerService = registerService;
    }

    @Autowired
    @Qualifier("validateCode_ServiceImpl")
    public void setValidatecode_service(Validatecode_Service validatecode_service) {
        this.validatecode_service = validatecode_service;
    }

    @PostMapping("/sendValidationCode")
    public String sendValidationCode(@RequestParam("e_mail") String E_mail) {
        //E_mail = "1748383809@qq.com";
        String Name = "新用户";
        //配置time计时器
        final Timer timer = new Timer();
        //发送验证码
        new Thread(() -> ValidationCode = validatecode_service.sendValidateCode(E_mail, Name, ActivityStatus.REGISTER_MAIL)).start();
        //如果发送失败的话就直接返回，否则则再启动定时器
        if (ValidationCode.equals(ActivityStatus.STRING_MAIL_SEND_FAILED)) {
            return ActivityStatus.STRING_MAIL_SEND_FAILED;
        } else {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    ValidationCode = ActivityStatus.STRING_VALIDATION_CODE_DATED;
                    timer.cancel();
                }
            }, 2 * 60 * 1000);//延时是：2分钟，即1000ms是1s，60s是一分钟，乘以2是两分钟
            return ActivityStatus.STRING_MAIL_SEND_SUCCESS;
        }
    }

    @PostMapping("/validateCode")
    public String validateCode(@RequestParam("inputValidateCode") String inputValidateCode) {
        return validatecode_service.validateCode(ValidationCode, inputValidateCode);
    }

    @PostMapping("/register")
    public String register(@RequestParam("user_info") String JSON_UserInfo, @RequestParam("login_info") String JSON_LoginInfo) {
        /*UserInfo userInfotest = new UserInfo();
        userInfotest.setE_mail("1748383808@qq.com");
        userInfotest.setAccount("19230411");
        userInfotest.setRegisterTime(new Date());
        userInfotest.setHeadPicturePath(StorageState.DEFAULT_HEAD_PIC);
        userInfotest.setHeadPictureSize(StorageState.DEFAULT_HEAD_PIC_SIZE);
        userInfotest.setName("张总测试");
        userInfotest.setSex("男");
        LoginInfo loginInfotest = new LoginInfo();
        loginInfotest.setAccount("192030411");
        loginInfotest.setE_mail("1748383808@qq.com");
        loginInfotest.setPassword("password_test");
        loginInfotest.setName("张总测试");
        JSON_UserInfo = JSON.toJSONString(userInfotest);
        JSON_LoginInfo = JSON.toJSONString(loginInfotest);

        //loginInfo里的email更新为刚才验证成功的email
        userInfo.setE_mail(UserE_Mail);*/
        UserInfo userInfo = JSON.parseObject(JSON_UserInfo, UserInfo.class);
        LoginInfo loginInfo = JSON.parseObject(JSON_LoginInfo, LoginInfo.class);
        if (registerService.Register(userInfo, loginInfo) == ActivityStatus.REGISTER_SUCCESS)
            return ActivityStatus.STRING_REGISTER_SUCCESS;
        else
            return ActivityStatus.STRING_REGISTER_FAILED;
    }


}
