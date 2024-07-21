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
import java.net.URLEncoder;

public class RegisterService {

    public void sendValidationCode(Handler handler, String e_mail) {
        final boolean[] serverNotConnected = {true};
        new Thread() {
            @Override
            public void run() {
                while (serverNotConnected[0]){
                    try {
                        HttpURLConnection connection = HttpUtils.getUrlConnection(NetWorkProperties.REGISTER_SEND_VALIDATION_CODE, "POST");
                        String url_message = "e_mail=" + URLEncoder.encode(e_mail, "UTF-8");
                        connection.getOutputStream().write(url_message.getBytes());
                        if (connection.getResponseCode() == 200) {
                            serverNotConnected[0] =false;
                            String str = StreamUtils.GetStringFromServer(connection.getInputStream());
                            HandlerUtils.sendStringMessage("register_send_validation_code_status", str, handler, ThreadState.REGISTER_SEND_VALIDATION_CODE);
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
                    HttpURLConnection connection = HttpUtils.getUrlConnection(NetWorkProperties.REGISTER_VALIDATE_CODE, "POST");
                    String url_message = "inputValidateCode=" + URLEncoder.encode(validateCode, "UTF-8");
                    connection.getOutputStream().write(url_message.getBytes());
                    if (connection.getResponseCode() == 200) {
                        String str = StreamUtils.GetStringFromServer(connection.getInputStream());
                        HandlerUtils.sendStringMessage("register_validate_code_status", str, handler, ThreadState.REGISTER_VALIDATE_CODE);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    HandlerUtils.sendStringMessage("register_validate_code_status", "register_validate_code_error", handler, ThreadState.REGISTER_VALIDATE_CODE);
                }
            }
        }.start();
    }

    public void register(Handler handler, UserInfo userInfo, LoginInfo loginInfo) {
        new Thread() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection = HttpUtils.getUrlConnection(NetWorkProperties.REGISTER_REGISTER, "POST");
                    String url_message = "user_info=" + URLEncoder.encode(JSON.toJSONString(userInfo), "UTF-8") + "&login_info=" + URLEncoder.encode(JSON.toJSONString(loginInfo), "UTF-8");
                    connection.getOutputStream().write(url_message.getBytes());
                    if (connection.getResponseCode() == 200) {
                        String str = StreamUtils.GetStringFromServer(connection.getInputStream());
                        HandlerUtils.sendStringMessage("register_register_status", str, handler, ThreadState.REGISTER_REGISTER);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    HandlerUtils.sendStringMessage("register_register_status", "register_register_error", handler, ThreadState.REGISTER_REGISTER);
                }
            }
        }.start();
    }


}
