package com.bai.HolyIns.service;

import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.bai.HolyIns.pojo.LoginInfo;
import com.bai.HolyIns.pojo.UserInfo;
import com.bai.HolyIns.state.NetWorkProperties;
import com.bai.HolyIns.state.ThreadState;
import com.bai.HolyIns.utils.HandlerUtils;
import com.bai.HolyIns.utils.HttpUtils;
import com.bai.HolyIns.utils.StreamUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;

public class FindPswService {

    public void sendValidationCode(Handler handler, String e_mail) {
        final boolean[] serverNotConnected = {true};
        new Thread() {
            @Override
            public void run() {
                while (serverNotConnected[0]) {
                    try {
                        HttpURLConnection connection = HttpUtils.getUrlConnection(NetWorkProperties.FIND_PSW_SEND_VALIDATION_CODE, "POST");
                        String url_message = "e_mail=" + URLEncoder.encode(e_mail, "UTF-8");
                        connection.getOutputStream().write(url_message.getBytes());
                        if (connection.getResponseCode() == 200) {
                            serverNotConnected[0] = false;
                            String str = StreamUtils.GetStringFromServer(connection.getInputStream());
                            HandlerUtils.sendStringMessage("find_psw_send_validation_code_status", str, handler, ThreadState.FIND_PSW_SEND_VALIDATION_CODE);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public void validateCode(Handler handler, String validateCode) {
        new Thread() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection = HttpUtils.getUrlConnection(NetWorkProperties.FIND_PSW_VALIDATE_CODE, "POST");
                    String url_message = "inputValidateCode=" + URLEncoder.encode(validateCode, "UTF-8");
                    connection.getOutputStream().write(url_message.getBytes());
                    if (connection.getResponseCode() == 200) {
                        String str = StreamUtils.GetStringFromServer(connection.getInputStream());
                        HandlerUtils.sendStringMessage("find_psw_validate_code_status", str, handler, ThreadState.FIND_PSW_VALIDATE_CODE);
                    }
                }  catch (IOException e) {
                    e.printStackTrace();
                    //连接超时就返回验证验证码失败
                    HandlerUtils.sendStringMessage("find_psw_validate_code_status", "find_psw_validate_code_error", handler, ThreadState.FIND_PSW_VALIDATE_CODE);
                }
            }
        }.start();
    }

    public void changePsw(Handler handler, String new_password, String email) {
        new Thread() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection = HttpUtils.getUrlConnection(NetWorkProperties.FIND_PSW_CHANGE_PSW, "POST");
                    String url_message = "new_password=" + URLEncoder.encode(new_password, "UTF-8") + "&e_mail=" + URLEncoder.encode(email, "UTF-8");
                    connection.getOutputStream().write(url_message.getBytes());
                    if (connection.getResponseCode() == 200) {
                        String str = StreamUtils.GetStringFromServer(connection.getInputStream());
                        HandlerUtils.sendStringMessage("find_psw_change_psw_status", str, handler, ThreadState.FIND_PSW_CHANGE_PSW);
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                    //连接超时就返回修改密码失败
                    HandlerUtils.sendStringMessage("find_psw_change_psw_status", "find_psw_change_psw_error", handler, ThreadState.FIND_PSW_CHANGE_PSW);
                }
            }
        }.start();
    }

}
