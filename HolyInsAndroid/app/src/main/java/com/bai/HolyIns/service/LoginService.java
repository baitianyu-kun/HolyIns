package com.bai.HolyIns.service;

import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.bai.HolyIns.pojo.LoginInfo;
import com.bai.HolyIns.state.NetWorkProperties;
import com.bai.HolyIns.state.ThreadState;
import com.bai.HolyIns.utils.HandlerUtils;
import com.bai.HolyIns.utils.HttpUtils;
import com.bai.HolyIns.utils.StreamUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;


public class LoginService {

    public void Login(Handler handler, LoginInfo loginInfo) {
        final boolean[] serverNotConnected = {true};
        new Thread(){
            @Override
            public void run() {
                //如果没有连接上服务器就一直连接
                while (serverNotConnected[0]){
                    try {
                        HttpURLConnection connection= HttpUtils.getUrlConnection(NetWorkProperties.LOGIN,"POST");
                        String url_message="login_info="+ URLEncoder.encode(JSON.toJSONString(loginInfo),"UTF-8");
                        connection.getOutputStream().write(url_message.getBytes());
                        if (connection.getResponseCode()==200){
                            serverNotConnected[0] =false;
                            String str= StreamUtils.GetStringFromServer(connection.getInputStream());
                            HandlerUtils.sendStringMessage("login_status",str,handler, ThreadState.LOGIN);
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

}
