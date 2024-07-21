package com.bai.service.mailsend_service;

public interface MailSendService {
    //发送邮件
    int sendMail(String destination,String subject,String destination_name,String destination_code,String templateName);
    //发送密码验证邮件
    int sendPswValidationMail(String destination_account,String destination_name,String destination_code);
    //发送注册验证邮件
    int sendRegisterMail(String destination_account,String destination_name,String destination_code);
    //cgbhycnfcqnwcjah    174
    //cyqechyhjvyabaij    7466
}
