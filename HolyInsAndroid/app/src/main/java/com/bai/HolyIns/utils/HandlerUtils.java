package com.bai.HolyIns.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;

import java.io.Serializable;

public class HandlerUtils {
    //将要传输的数据放入bundle传到handler那里
    /*
     *
     * key,bundle要传的key，value就是值，handler就是监听器
     * @return none
     * @author BaiTianyu
     * @creed: ThanksForLooking
     * @date
     */
    public static void sendStringMessage(String key, String value, Handler handler,int message_what) {
        Bundle bundle = new Bundle();
        bundle.putString(key, value);
        Message message = handler.obtainMessage();
        message.setData(bundle);
        message.what=message_what;
        handler.sendMessage(message);
    }
    public static void sendSerializableMessage(String key, Serializable value, Handler handler, int message_what){
        Bundle bundle = new Bundle();
        bundle.putSerializable(key, value);
        Message message = handler.obtainMessage();
        message.setData(bundle);
        message.what=message_what;
        handler.sendMessage(message);
    }
    public static void sendIntMessage(String key, int value, Handler handler,int message_what){
        Bundle bundle = new Bundle();
        bundle.putInt(key, value);
        Message message = handler.obtainMessage();
        message.setData(bundle);
        message.what=message_what;
        handler.sendMessage(message);
    }
    public static void sendParcelableMessage(String key, Parcelable value, Handler handler, int message_what){
        Bundle bundle = new Bundle();
        bundle.putParcelable(key, value);
        Message message = handler.obtainMessage();
        message.setData(bundle);
        message.what=message_what;
        handler.sendMessage(message);
    }
}
