package com.bai.HolyIns.thread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.bai.HolyIns.state.NetWorkProperties;
import com.bai.HolyIns.state.ThreadState;
import com.bai.HolyIns.utils.HandlerUtils;

import java.io.IOException;
import java.net.URL;

public class ImageGetThread1 extends Thread{
    private Handler handler;
    private Bitmap bitmap;
    private String imageUrl;
    private int imageType;
    public ImageGetThread1(Handler handler,String imageUrl,int imageType){
        this.handler=handler;
        this.imageUrl=imageUrl;
        this.imageType=imageType;
    }
    @Override
    public void run() {
        URL picUrl = null;
        try {
            if (imageType== ThreadState.HEAD_PIC){
                picUrl = new URL(NetWorkProperties.URL+NetWorkProperties.GET_HEAD_PIC+"?headPicPath="+imageUrl);
            }else{
                picUrl = new URL(NetWorkProperties.URL+NetWorkProperties.GET_PHOTO+"?photoPath="+imageUrl);
            }
            //这个方法只支持get方法获取
            bitmap = BitmapFactory.decodeStream(picUrl.openStream());
            HandlerUtils.sendParcelableMessage("bitmap",bitmap,handler,imageType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
