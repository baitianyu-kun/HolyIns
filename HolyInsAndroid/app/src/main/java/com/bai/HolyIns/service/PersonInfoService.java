package com.bai.HolyIns.service;

import android.os.Handler;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.bai.HolyIns.pojo.UserInfo;
import com.bai.HolyIns.state.NetWorkProperties;
import com.bai.HolyIns.state.ThreadState;
import com.bai.HolyIns.utils.HandlerUtils;
import com.bai.HolyIns.utils.HttpUtils;
import com.bai.HolyIns.utils.StreamUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

public class PersonInfoService {

    public void findOwnPost(int now_user_id, Handler handler) {
        final boolean[] serverNotConnected = {true};
        new Thread() {
            @Override
            public void run() {
                while (serverNotConnected[0]) {
                    try {
                        HttpURLConnection connection = HttpUtils.getUrlConnection(NetWorkProperties.FIND_OWN_POST, "POST");
                        String url_message = "now_user_id=" + now_user_id;
                        connection.getOutputStream().write(url_message.getBytes());
                        if (connection.getResponseCode() == 200) {
                            serverNotConnected[0] = false;
                            String str = StreamUtils.GetStringFromServer(connection.getInputStream());
                            HandlerUtils.sendStringMessage("findOwnPost_status", str, handler, ThreadState.FIND_OWN_POST);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public void changePersonInfo(Handler handler, UserInfo userInfo) {
        final boolean[] serverNotConnected = {true};
        new Thread() {
            @Override
            public void run() {
                while (serverNotConnected[0]) {
                    try {
                        HttpURLConnection connection = HttpUtils.getUrlConnection(NetWorkProperties.CHANGE_PERSON_INFO, "POST");
                        String url_message = "user_info=" + URLEncoder.encode(JSON.toJSONString(userInfo), "UTF-8");
                        connection.getOutputStream().write(url_message.getBytes());
                        if (connection.getResponseCode() == 200) {
                            serverNotConnected[0] = false;
                            String str = StreamUtils.GetStringFromServer(connection.getInputStream());
                            HandlerUtils.sendStringMessage("register_change_person_info_status", str, handler, ThreadState.CHANGE_PERSON_INFO);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public void findOwnLikePost(int now_user_id, Handler handler) {
        final boolean[] serverNotConnected = {true};
        new Thread() {
            @Override
            public void run() {
                while (serverNotConnected[0]) {
                    try {
                        HttpURLConnection connection = HttpUtils.getUrlConnection(NetWorkProperties.FIND_OWN_LIKE_POST, "POST");
                        String url_message = "now_user_id=" + now_user_id;
                        connection.getOutputStream().write(url_message.getBytes());
                        if (connection.getResponseCode() == 200) {
                            serverNotConnected[0] =false;
                            String str = StreamUtils.GetStringFromServer(connection.getInputStream());
                            HandlerUtils.sendStringMessage("findOwnLikePost_status", str, handler, ThreadState.FIND_OWN_LIKE_POST);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public void findOwnForwardingPost(int now_user_id, Handler handler) {
        final boolean[] serverNotConnected = {true};
        new Thread() {
            @Override
            public void run() {
                while (serverNotConnected[0]){
                    try {
                        HttpURLConnection connection = HttpUtils.getUrlConnection(NetWorkProperties.FIND_OWN_FORWARDING_POST, "POST");
                        String url_message = "now_user_id=" + now_user_id;
                        connection.getOutputStream().write(url_message.getBytes());
                        if (connection.getResponseCode() == 200) {
                            serverNotConnected[0] =false;
                            String str = StreamUtils.GetStringFromServer(connection.getInputStream());
                            HandlerUtils.sendStringMessage("findOwnForwardingPost_status", str, handler, ThreadState.FIND_OWN_FORWARDING_POST);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

}
