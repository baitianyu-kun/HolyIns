package com.bai.controller;

import com.bai.state.StorageState;
import org.junit.jupiter.api.Test;

import java.io.File;

public class getHeadPicTest {
    @Test
    public void getHeadPicTest() {
        File file = new File(StorageState.DEFAULT_HEAD_PIC);
        System.out.println(file.getName());
    }

}
