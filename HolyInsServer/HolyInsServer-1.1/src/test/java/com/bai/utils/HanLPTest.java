package com.bai.utils;

import com.hankcs.hanlp.HanLP;
import org.junit.jupiter.api.Test;

import java.util.List;

public class HanLPTest {
    @Test
    public void SplitTest(){
        String content = "66666";
        List<String> keywordList = HanLP.extractKeyword(content, 5);
    }
}
