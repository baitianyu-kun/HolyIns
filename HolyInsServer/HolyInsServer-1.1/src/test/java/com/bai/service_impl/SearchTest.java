package com.bai.service_impl;

import com.bai.config.AppConfig;
import com.bai.pojo.Post;
import com.bai.pojo.UserPost;
import com.bai.service_impl.search_serviceImpl.SearchServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchTest {
    @Test
    public void findPostByHashtagTest() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        SearchServiceImpl searchServiceImpl = context.getBean("searchServiceImpl", SearchServiceImpl.class);
        HashMap<String, List<Integer>> stringListHashMap = new HashMap<>();
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);
        stringListHashMap.put("hashtagIDs", integers);
        for (Integer integer : searchServiceImpl.findPhotoIDByHashTagID(stringListHashMap)) {
            System.out.println(integer);
        }
    }
    @Test
    public void findPostByPhotoDescriptionTest(){
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        SearchServiceImpl searchServiceImpl = context.getBean("searchServiceImpl", SearchServiceImpl.class);
        for (UserPost nice : searchServiceImpl.findUserPostByPhotoDescribe("nice")) {
            System.err.println(nice);
        }
    }
}
