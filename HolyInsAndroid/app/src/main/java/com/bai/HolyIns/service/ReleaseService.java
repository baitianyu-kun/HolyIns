package com.bai.HolyIns.service;

import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.bai.HolyIns.pojo.Photo;
import com.bai.HolyIns.state.NetWorkProperties;
import com.bai.HolyIns.state.ThreadState;
import com.bai.HolyIns.utils.HandlerUtils;
import com.bai.HolyIns.utils.HttpUtils;
import com.bai.HolyIns.utils.StreamUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;


public class ReleaseService {
    public void releasePost(Handler handler, Photo photo){
        new Thread(){
            @Override
            public void run() {
                try {
                    HttpURLConnection connection= HttpUtils.getUrlConnection(NetWorkProperties.RELEASE_POST,"POST");
                    String url="photo="+ URLEncoder.encode(JSON.toJSONString(photo),"UTF-8");
                    connection.getOutputStream().write(url.getBytes());
                    if (connection.getResponseCode()==200){
                        String str= StreamUtils.GetStringFromServer(connection.getInputStream());
                        HandlerUtils.sendStringMessage("releasePost_status",str,handler, ThreadState.RELEASE_POST);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                    HandlerUtils.sendStringMessage("releasePost_status","releasePost_error",handler, ThreadState.RELEASE_POST);
                }
            }
        }.start();
    }
}
