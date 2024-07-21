package com.bai.service_impl.validatecode_serviceImpl;

import com.bai.service.mailsend_service.MailSendService;
import com.bai.service.validatecode_service.Validatecode_Service;
import com.bai.state.ActivityStatus;
import com.bai.utils.IntegerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Properties;

@Service
public class ValidateCode_ServiceImpl implements Validatecode_Service {

    private MailSendService mailSendService;

    @Autowired
    public void setMailSendService(MailSendService mailSendService) {
        this.mailSendService = mailSendService;
    }

    @Override
    /*
     * @Description:
     * @Param: [E_mail, Name,sendType] 用户email和姓名和邮件发送的类型
     * @Return: java.lang.String
     * @Author: baitianyu
     * @Date: 2021/4/3 11:36
     **/

    public String sendValidateCode(String E_mail, String Name, int sendType) {
        int mail_send_status = 0;
        String ValidationCode = null;
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream("mail_config/mailConfig.properties"));
        } catch (IOException e) {
            return ActivityStatus.STRING_PROPERTY_FILE_NOT_FOUND;
        }
        //生成验证码
        ValidationCode = IntegerUtils.GenerateAnyLenRandomNumber(Integer.parseInt(properties.getProperty("validationCodeLen")));
        //判断发送类型
        if (sendType == ActivityStatus.REGISTER_MAIL) {
            mail_send_status = mailSendService.sendRegisterMail(E_mail, Name, ValidationCode);
        } else {
            mail_send_status = mailSendService.sendPswValidationMail(E_mail, Name, ValidationCode);
        }
        //发送结果
        if (mail_send_status == ActivityStatus.MAIL_SEND_SUCCESS) {
            return ValidationCode;
        } else {
            return ActivityStatus.STRING_MAIL_SEND_FAILED;
        }
    }

    @Override
    /*
     * @Description:
     * @Param: [validateCodeSend, validateCodeInput] 发送的验证码，输入的验证码
     * @Return: java.lang.String 若传来的验证码的值为过期，则返回过期，若不过期则去判断验证码是否输入正确
     * @Author: baitianyu
     * @Date: 2021/4/3 11:35
     **/

    public String validateCode(String validateCodeSend, String validateCodeInput) {
        if (validateCodeSend.equals(ActivityStatus.STRING_VALIDATION_CODE_DATED)) {
            return ActivityStatus.STRING_VALIDATION_CODE_DATED;
        } else {
            if (validateCodeSend.equals(validateCodeInput))
                return ActivityStatus.STRING_VALIDATION_CODE_RIGHT;
            else
                return ActivityStatus.STRING_VALIDATION_CODE_ERROR;
        }
    }
}
