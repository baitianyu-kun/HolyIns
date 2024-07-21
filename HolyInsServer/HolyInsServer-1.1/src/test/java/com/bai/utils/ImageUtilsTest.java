package com.bai.utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;


public class ImageUtilsTest {
    @Test
    public void ImageUtilsTest() {
        try {
            byte[] imageByte = ImageUtils.readImageToByteArray("D:\\test.png");
            ImageUtils.writeByteArrayToImage(imageByte, "D:\\new_test.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        ArrayList<String> strings = new ArrayList<>();
    }
}
