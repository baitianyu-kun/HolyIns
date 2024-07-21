package com.bai.service_impl;

import com.alibaba.fastjson.JSON;
import com.bai.config.AppConfig;
import com.bai.pojo.Post;
import com.bai.pojo.PostSmall;
import com.bai.pojo.UserInfo;
import com.bai.pojo.UserPost;
import com.bai.service_impl.base_serviceImpl.BaseServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseServiceTest {
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    public void BaseMapperTest() {
        BaseServiceImpl baseServiceImpl = context.getBean("baseServiceImpl", BaseServiceImpl.class);

        //1.单个
        Map<String, String> id_map = new HashMap<>();
        id_map.put("user_id", "1");
        Map<String, String> account_map = new HashMap<>();
        account_map.put("account", "123");
        Map<String, String> email_map = new HashMap<>();
        email_map.put("e_mail", "231");

        //System.err.println(baseServiceImpl.findUser(id_map));
        //System.err.println(baseServiceImpl.findUser(account_map));
        //System.err.println(baseServiceImpl.findUser(email_map));

        //2.list多个
        Map<String, List<String>> id_listmap = new HashMap<>();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1");
        arrayList.add("2");
        id_listmap.put("user_ids", arrayList);

        Map<String, List<String>> account_listmap = new HashMap<>();
        ArrayList<String> arrayList1 = new ArrayList<>();
        arrayList1.add("111");
        arrayList1.add("123");
        account_listmap.put("accounts", arrayList1);

        Map<String, List<String>> email_listmap = new HashMap<>();
        ArrayList<String> arrayList2 = new ArrayList<>();
        arrayList2.add("111");
        arrayList2.add("231");
        email_listmap.put("e_mails", arrayList2);


    }

    @Test
    public void findAllPostsByUserID() {
        BaseServiceImpl baseServiceImpl = context.getBean("baseServiceImpl", BaseServiceImpl.class);
        List<Post> allPostsByUserID = baseServiceImpl.findAllPostsByUserID(1);
        for (Post post : allPostsByUserID) {
            System.out.println(post);
        }
    }

    @Test
    public void findSpecificPostByPhotoIDTest() {
        BaseServiceImpl baseServiceImpl = context.getBean("baseServiceImpl", BaseServiceImpl.class);
        Post specificPostByPhotoID = baseServiceImpl.findSpecificPostByPhotoID(1);
        System.out.println(specificPostByPhotoID);
    }

    @Test
    public void findSpecificUserPostByPhotoIDTest(){
        BaseServiceImpl baseServiceImpl = context.getBean("baseServiceImpl", BaseServiceImpl.class);
        UserPost specificPostByPhotoID = baseServiceImpl.findSpecificUserPostByPhotoID(2);
        System.out.println(specificPostByPhotoID);
    }
}
