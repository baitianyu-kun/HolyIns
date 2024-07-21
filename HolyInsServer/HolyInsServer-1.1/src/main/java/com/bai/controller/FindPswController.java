package com.bai.controller;

import com.bai.pojo.LoginInfo;
import com.bai.service.findpsw_service.FindPswService;
import com.bai.service.mailsend_service.MailSendService;
import com.bai.service.validatecode_service.Validatecode_Service;
import com.bai.state.ActivityStatus;
import com.bai.utils.IntegerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

@RequestMapping("/findpsw")
@RestController
public class FindPswController {
    private String ValidationCode = ActivityStatus.STRING_VALIDATION_CODE_DATED;

    private FindPswService findPswService;
    private Validatecode_Service validatecode_service;

    @Autowired
    @Qualifier("findPswServiceImpl")
    public void setFindPswService(FindPswService findPswService) {
        this.findPswService = findPswService;
    }

    @Autowired
    @Qualifier("validateCode_ServiceImpl")
    public void setValidatecode_service(Validatecode_Service validatecode_service) {
        this.validatecode_service = validatecode_service;
    }

    @PostMapping("/sendValidationCode")
    public String sendValidationCode(@RequestParam("e_mail") String E_mail) {
        //配置time计时器
        final Timer timer = new Timer();
        //获取用户名
        String user_name = findPswService.ReturnUserName(E_mail);
        new Thread(() -> ValidationCode = validatecode_service.sendValidateCode(E_mail, user_name, ActivityStatus.PSW_VALIDATION_MAIL)).start();
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

    @PostMapping("/changePsw")
    public String changePsw(@RequestParam("new_password") String new_password, @RequestParam("e_mail") String user_email) {
        if (findPswService.FindPsw(new LoginInfo(user_email, new_password)) == ActivityStatus.PASSWORD_CHANGE_SUCCESS) {
            return ActivityStatus.STRING_PASSWORD_CHANGE_SUCCESS;
        } else {
            return ActivityStatus.STRING_PASSWORD_CHANGE_FAILED;
        }
    }
}
