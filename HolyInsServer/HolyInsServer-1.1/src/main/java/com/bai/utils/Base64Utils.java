package com.bai.utils;

import java.io.*;
import java.util.Base64;

public class Base64Utils {

    public static String ByteToBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static byte[] Base64ToByte(String base64_str) {
        return Base64.getDecoder().decode(base64_str);
    }

    /**
     * @Description:
     * @Param: [FilePath, base64str] 文件目录，base64编码值
     * @Return: boolean
     * @Author: baitianyu
     * @Date: 2021/3/22 10:20
     **/

    public static boolean Base64ToImage(String FilePath, String base64str) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(FilePath);
            out.write(Base64ToByte(base64str));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @Description:
     * @Param: [FilePath] 文件目录
     * @Return: java.lang.String
     * @Author: baitianyu
     * @Date: 2021/3/22 10:20
     **/

    public static String ImageToBase64(String FilePath) {
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(FilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ByteToBase64(data);
    }
}
