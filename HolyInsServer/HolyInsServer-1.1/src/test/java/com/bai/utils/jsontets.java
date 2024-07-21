package com.bai.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

public class jsontets {
    @Test
    public void jsontest(){
        List<Integer>list=new ArrayList<>();
        list.add(1);
        list.add(2);
        System.out.println(JSON.toJSONString(list));
    }
}
