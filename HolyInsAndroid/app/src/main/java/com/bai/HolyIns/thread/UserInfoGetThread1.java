package com.bai.HolyIns.thread;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.bai.HolyIns.state.NetWorkProperties;
import com.bai.HolyIns.state.ThreadState;
import com.bai.HolyIns.utils.HandlerUtils;
import com.bai.HolyIns.utils.HttpUtils;
import com.bai.HolyIns.utils.StreamUtils;

import java.io.IOException;
import java.net.HttpURLConnection;

public class UserInfoGetThread1 extends Thread {
    private Handler handler;
    private int userId;

    public UserInfoGetThread1(Handler handler, int userId) {
        this.handler = handler;
        this.userId = userId;

    }

    @Override
    public void run() {
        try {
            HttpURLConnection connection = HttpUtils.getUrlConnection(NetWorkProperties.FIND_USER_BY_ID, "POST");
            String url_message = "user_id=" + userId;
            connection.getOutputStream().write(url_message.getBytes());
            if (connection.getResponseCode() == 200) {
                String str = StreamUtils.GetStringFromServer(connection.getInputStream());
                System.out.println("str=" + str);
                HandlerUtils.sendStringMessage("user_info", str, handler, ThreadState.USER_INFO);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
