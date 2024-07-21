package com.bai.HolyIns.utils;

import com.bai.HolyIns.state.NetWorkProperties;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
    public static HttpURLConnection getUrlConnection(String mapping, String RequestType) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(NetWorkProperties.URL + mapping);
            //SpringUrl测试
            connection = (HttpURLConnection) url.openConnection();
            //设置属性
            connection.setRequestMethod(RequestType);
            connection.setReadTimeout(2000);
            connection.setConnectTimeout(2000);
            //设置输入流和输出流,都设置为true
            connection.setDoOutput(true);
            connection.setDoInput(true);
            //设置http请求头(可以参照:http://tools.jb51.net/table/http_header)
            //connection.setRequestProperty("Accept-Encoding","gzip, deflate, br");
            connection.setRequestProperty("Accept","*/*");
            //connection.setRequestProperty("Accept", "text/plain;charset=UTF-8, text/html,text/json");//指定客户端能够接收的内容类型
            connection.setRequestProperty("Connection", "keep-alive"); //http1.1

        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
