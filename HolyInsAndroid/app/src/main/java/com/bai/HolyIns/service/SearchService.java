package com.bai.HolyIns.service;

import android.os.Handler;

import com.bai.HolyIns.state.NetWorkProperties;
import com.bai.HolyIns.state.ThreadState;
import com.bai.HolyIns.utils.HandlerUtils;
import com.bai.HolyIns.utils.HttpUtils;
import com.bai.HolyIns.utils.StreamUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

public class SearchService {
    public void searchUser(String search_condition, Handler handler) {
        new Thread() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection = HttpUtils.getUrlConnection(NetWorkProperties.FIND_ALL_USER, "POST");
                    String url_message = "search_condition=" + URLEncoder.encode(search_condition, "UTF-8");
                    connection.getOutputStream().write(url_message.getBytes());
                    if (connection.getResponseCode() == 200) {
                        String str = StreamUtils.GetStringFromServer(connection.getInputStream());
                        HandlerUtils.sendStringMessage("search_user_status", str, handler, ThreadState.FIND_ALL_USER);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void searchPostByPhotoDescription(String photo_description, Handler handler) {
        new Thread() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection = HttpUtils.getUrlConnection(NetWorkProperties.FIND_POST_BY_PHOTO_DESCRIBE, "POST");
                    String url_message = "photo_description=" + URLEncoder.encode(photo_description, "UTF-8");
                    connection.getOutputStream().write(url_message.getBytes());
                    if (connection.getResponseCode() == 200) {
                        String str = StreamUtils.GetStringFromServer(connection.getInputStream());
                        HandlerUtils.sendStringMessage("searchPostByPhotoDescription_status", str, handler, ThreadState.FIND_POST_BY_PHOTO_DESCRIPTION);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
