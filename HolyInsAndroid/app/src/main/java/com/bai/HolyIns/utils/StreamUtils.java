package com.bai.HolyIns.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamUtils {
    /*
    stream工具类，用来处理从服务器返回的各种字节流
     */
    /*public static String GetStringFromServer(InputStream inputStream)
    {
        String str="";
        try {
            byte[] buffer = new byte[102400000];
            int length = 0;
            while ((length = inputStream.read(buffer)) != -1) {
                str = new String(buffer, 0, length);
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return str;
    }*/
    public static String GetStringFromServer(InputStream inputStream)
    {
        StringBuffer buffer=new StringBuffer();
        InputStreamReader inputStreamReader;
        try {
            inputStreamReader=new InputStreamReader(inputStream,"utf-8");
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String str=null;
            while ((str=bufferedReader.readLine())!=null)
            {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return buffer.toString();
    }
    public static int GetIntFromServer(InputStream inputStream)
    {
        int ints=0;
        try {
            ints=inputStream.read();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return ints;
    }
}
