package com.bai.utils;

import org.junit.jupiter.api.Test;

public class StringTest {
    @Test
    public void replaceTest(){
        String test="E:\\holyins\\photo\\77f61343e4d540ebbc5f9068e93f9f0b.jpg";
        String test2=test.replaceAll("\\\\","/");
        System.out.println(test2);
    }
}
