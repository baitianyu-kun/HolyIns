package com.bai.utils;

import org.apache.commons.io.FileUtils;

import java.io.*;

public class ImageUtils {
    //org.apache.commons.io.FileUtils转换为byte[]
    public static byte[] readImageToByteArray(String imagePath) throws IOException {
        return FileUtils.readFileToByteArray(new File(imagePath));
    }

    public static void writeByteArrayToImage(byte[] data,String imagePath) throws IOException {
        FileUtils.writeByteArrayToFile(new File(imagePath),data);
    }

    public static byte[] readImageToByteArray(File file) throws IOException{
        return FileUtils.readFileToByteArray(file);
    }
}
