package com.bai.HolyIns.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.bai.HolyIns.pojo.UploadImageInfo;
import com.bai.HolyIns.state.NetWorkProperties;
import com.bai.HolyIns.state.ThreadState;
import com.bai.HolyIns.utils.HandlerUtils;
import com.bai.HolyIns.utils.UploadUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageService {
    //上传头像
    public void HeadPicUpload(File file, Handler handler) {
        final boolean[] serverNotConnected = {true};
        new Thread() {
            @Override
            public void run() {
                while (serverNotConnected[0]) {
                    UploadImageInfo uploadImageInfo = ImageUpload(file, NetWorkProperties.URL + NetWorkProperties.UPLOAD_HEAD_PIC, "headpic");
                    if (!uploadImageInfo.getImage_path().equals("null")) {
                        //如果上传回来的信息的imagepath不为null的话就退出，否则则一直连接服务器
                        HandlerUtils.sendSerializableMessage("head_pic_upload_status", uploadImageInfo, handler, ThreadState.UPLOAD_HEAD_PIC);
                        serverNotConnected[0] = false;
                    }
                }
            }
        }.start();

    }

    //上传照片
    public void PhotoUpload(File file, Handler handler) {
        final boolean[] serverNotConnected = {true};
        new Thread() {
            @Override
            public void run() {
                while (serverNotConnected[0]) {
                    UploadImageInfo uploadImageInfo = ImageUpload(file, NetWorkProperties.URL + NetWorkProperties.UPLOAD_PHOTO, "photo");
                    if (!uploadImageInfo.getImage_path().equals("null")) {
                        //如果上传回来的信息的imagepath不为null的话就退出，否则则一直连接服务器
                        HandlerUtils.sendSerializableMessage("photo_upload_status", uploadImageInfo, handler, ThreadState.UPLOAD_PHOTO);
                        serverNotConnected[0] = false;
                    }
                }
            }
        }.start();
    }

    //上传图片公共
    public UploadImageInfo ImageUpload(File file, String requestURL, String requestParam) {
        String result = UploadUtil.uploadImage(file, requestURL, requestParam);
        //上传出错就返回一个错误的对象,防止handler那里putSerializable时候出错，因为那里不让放null值
        if (result.equals("error"))
            return new UploadImageInfo("null", 999999);
        else
            return JSON.parseObject(result, UploadImageInfo.class);
    }

    //获取发帖者头像
    public void HeadPicGet(Handler handler, String imageUrl) {
        ImageGet(handler, imageUrl, ThreadState.HEAD_PIC);
    }

    //获取照片
    public void PhotoGet(Handler handler, String imageUrl) {
        ImageGet(handler, imageUrl, ThreadState.PHOTO);
    }

    //获取当前用户头像
    public void HeadPicGetNowUser(Handler handler, String imageUrl) {
        ImageGet(handler, imageUrl, ThreadState.NOW_USER_HEAD_PIC);
    }

    //获取图片公共
    public void ImageGet(Handler handler, String imageUrl, int imageType) {
        new Thread() {
            @Override
            public void run() {
                URL picUrl = null;
                try {
                    if (imageType == ThreadState.HEAD_PIC) {
                        picUrl = new URL(NetWorkProperties.URL + NetWorkProperties.GET_HEAD_PIC + "?headPicPath=" + imageUrl);
                    } else {
                        picUrl = new URL(NetWorkProperties.URL + NetWorkProperties.GET_PHOTO + "?photoPath=" + imageUrl);
                    }
                    //这个方法只支持get方法获取
                    Bitmap bitmap = BitmapFactory.decodeStream(picUrl.openStream());
                    if (bitmap != null) {
                        //如果图片一直不获取成功的话就一直获取
                        HandlerUtils.sendParcelableMessage("bitmap", bitmap, handler, imageType);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}


