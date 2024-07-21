package com.bai.HolyIns.service;

import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.bai.HolyIns.state.NetWorkProperties;
import com.bai.HolyIns.state.ThreadState;
import com.bai.HolyIns.utils.HandlerUtils;
import com.bai.HolyIns.utils.HttpUtils;
import com.bai.HolyIns.utils.StreamUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;


public class BaseService {
    //获取用户信息
    public void UserInfoGet(Handler handler, int userId) {
        final boolean[] serverNotConnected = {true};
        new Thread() {
            @Override
            public void run() {
                while (serverNotConnected[0]) {
                    try {
                        HttpURLConnection connection = HttpUtils.getUrlConnection(NetWorkProperties.FIND_USER_BY_ID, "POST");
                        String url_message = "user_id=" + userId;
                        connection.getOutputStream().write(url_message.getBytes());
                        if (connection.getResponseCode() == 200) {
                            serverNotConnected[0] = false;
                            String str = StreamUtils.GetStringFromServer(connection.getInputStream());
                            //System.out.println("str=" + str);
                            HandlerUtils.sendStringMessage("user_info", str, handler, ThreadState.USER_INFO);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    //根据账号获取用户信息
    public void UserInfoGetByAccount(Handler handler, String account) {
        final boolean[] serverNotConnected = {true};
        new Thread() {
            @Override
            public void run() {
                while (serverNotConnected[0]) {
                    try {
                        HttpURLConnection connection = HttpUtils.getUrlConnection(NetWorkProperties.FIND_USER_BY_ACCOUNT, "POST");
                        String url_message = "user_account=" + account;
                        connection.getOutputStream().write(url_message.getBytes());
                        if (connection.getResponseCode() == 200) {
                            serverNotConnected[0] = false;
                            String str = StreamUtils.GetStringFromServer(connection.getInputStream());
                            HandlerUtils.sendStringMessage("user_info_by_account", str, handler, ThreadState.FIND_USER_BY_ACCOUNT);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    //根据邮箱获取用户信息
    public void UserInfoGetByEmail(Handler handler, String e_mail) {
        final boolean[] serverNotConnected = {true};
        new Thread() {
            @Override
            public void run() {
                while (serverNotConnected[0]) {
                    try {
                        HttpURLConnection connection = HttpUtils.getUrlConnection(NetWorkProperties.FIND_USER_BY_EMAIL, "POST");
                        String url_message = "user_email=" + e_mail;
                        connection.getOutputStream().write(url_message.getBytes());
                        if (connection.getResponseCode() == 200) {
                            serverNotConnected[0] = false;
                            String str = StreamUtils.GetStringFromServer(connection.getInputStream());
                            HandlerUtils.sendStringMessage("user_info_by_e_mail", str, handler, ThreadState.FIND_USER_BY_EMAIL);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    //id列表获取用户信息
    public void UserInfosGetByIDs(Handler handler, List<Integer> user_ids) {
        final boolean[] serverNotConnected = {true};
        new Thread() {
            @Override
            public void run() {
                while (serverNotConnected[0]) {
                    try {
                        HttpURLConnection connection = HttpUtils.getUrlConnection(NetWorkProperties.FIND_USER_BY_IDS, "POST");
                        String url_message = "user_ids=" + JSON.toJSONString(user_ids);
                        connection.getOutputStream().write(url_message.getBytes());
                        if (connection.getResponseCode() == 200) {
                            serverNotConnected[0] = false;
                            String str = StreamUtils.GetStringFromServer(connection.getInputStream());
                            HandlerUtils.sendStringMessage("user_infos", str, handler, ThreadState.FIND_USER_BY_IDS);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    //根据用户id获取其post
    public void getPostByUserID(Handler handler, int userId) {
        final boolean[] serverNotConnected = {true};
        new Thread() {
            @Override
            public void run() {
                while (serverNotConnected[0]) {
                    try {
                        HttpURLConnection conn = HttpUtils.getUrlConnection(NetWorkProperties.FIND_ALL_USER_POSTS_BY_USER_ID, "POST");
                        String url_message = "user_id=" + userId;
                        conn.getOutputStream().write(url_message.getBytes());
                        if (conn.getResponseCode() == 200) {
                            serverNotConnected[0] =false;
                            String str = StreamUtils.GetStringFromServer(conn.getInputStream());
                            HandlerUtils.sendStringMessage("get_post_by_user_id", str, handler, ThreadState.GET_POST_BY_USER_ID);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}

