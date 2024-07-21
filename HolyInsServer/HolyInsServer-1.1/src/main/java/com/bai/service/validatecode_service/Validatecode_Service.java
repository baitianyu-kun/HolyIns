package com.bai.service.validatecode_service;

public interface Validatecode_Service {
    //发送成功返回的是验证码，失败返回的是发送失败
    String sendValidateCode(String E_mail,String Name,int sendType);
    String validateCode(String validateCodeSend,String validateCodeInput);
}
