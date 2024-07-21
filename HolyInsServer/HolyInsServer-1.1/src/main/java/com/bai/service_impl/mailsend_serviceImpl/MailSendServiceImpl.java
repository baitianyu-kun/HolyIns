package com.bai.service_impl.mailsend_serviceImpl;

import com.bai.service.mailsend_service.MailSendService;
import com.bai.state.ActivityStatus;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
public class MailSendServiceImpl implements MailSendService {

    private JavaMailSender javaMailSender;
    private Configuration configuration;

    @Autowired
    @Qualifier("mailSender")//都写上吧，避免以后麻烦
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Autowired
    @Qualifier("freeMarkerConfiguration")//都写上吧，避免以后麻烦
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    /*
     * @Description:
     * @Param: [destination_account, subject, destination_name, destination_code, templateName]
     * @Return: int
     * @Author: baitianyu
     * @Date: 2021/5/6 21:59
     **/

    public int sendMail(String destination_account, String subject, String destination_name, String destination_code, String templateName) {
        MimeMessage message = javaMailSender.createMimeMessage();
        Properties properties = new Properties();
        try {
            //加载配置文件
            properties.load(this.getClass().getClassLoader().getResourceAsStream("mail_config/mailConfig.properties"));
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            //从哪来的
            helper.setFrom(properties.getProperty("useraccount"));
            //发给谁
            helper.setTo(destination_account);
            //邮件主题或者叫标题
            helper.setSubject(subject);
            //从模板里面读取
            helper.setText(getTemplateText(destination_name, destination_code, templateName), true);
            javaMailSender.send(message);
            return ActivityStatus.MAIL_SEND_SUCCESS;
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            return ActivityStatus.MAIL_SEND_FAILED;
        }
    }

    @Override
    /*
     * @Description:
     * @Param: [destination_account, destination_name, destination_code]
     * @Return: int
     * @Author: baitianyu
     * @Date: 2021/5/6 21:59
     **/

    public int sendPswValidationMail(String destination_account, String destination_name, String destination_code) {
        String subject = "请收好您的验证码";
        String templateName = "validationMail.ftl";
        if (sendMail(destination_account, subject, destination_name, destination_code, templateName) == ActivityStatus.MAIL_SEND_SUCCESS)
            return ActivityStatus.MAIL_SEND_SUCCESS;
        else
            return ActivityStatus.MAIL_SEND_FAILED;
    }

    @Override
    /*
     * @Description:
     * @Param: [destination_account, destination_name, destination_code]
     * @Return: int
     * @Author: baitianyu
     * @Date: 2021/5/6 21:59
     **/

    public int sendRegisterMail(String destination_account, String destination_name, String destination_code) {
        String subject = "欢迎注册HolyIns";
        String templateName = "registerMail.ftl";
        if (sendMail(destination_account, subject, destination_name, destination_code, templateName) == ActivityStatus.MAIL_SEND_SUCCESS)
            return ActivityStatus.MAIL_SEND_SUCCESS;
        else
            return ActivityStatus.MAIL_SEND_FAILED;
    }

    //读取模板
    private String getTemplateText(String name, String code, String templateName) {
        String txt = "";
        try {
            Template template = configuration.getTemplate(templateName);
            //通过map传递动态数据
            Map map = new HashMap();
            map.put("username", name);
            map.put("validateCode", code);
            //解析模板文件
            txt = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return txt;
    }
}
