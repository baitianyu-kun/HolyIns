package com.bai.service_impl;

import com.bai.config.AppConfig;
import com.bai.pojo.Comment;
import com.bai.pojo.Forwarding;
import com.bai.pojo.Likes;
import com.bai.pojo.Subscribe;
import com.bai.service_impl.content_serviceImpl.ContentServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;

public class ContentServiceTest {
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    public void LikeTest() {
        ContentServiceImpl contentServiceImpl = context.getBean("contentServiceImpl", ContentServiceImpl.class);
        Likes likes = new Likes();
        likes.setLike_user_id(9);
        likes.setDate_created(new Date());
        likes.setDate_updated(new Date());
        int photo_id = 10;
        likes.setPhoto_id(photo_id);
        int i = contentServiceImpl.LikePost(likes);
        System.out.println(i);
    }

    @Test
    public void SubscribeTest() {
        ContentServiceImpl contentServiceImpl = context.getBean("contentServiceImpl", ContentServiceImpl.class);
        int subscribe = contentServiceImpl.Subscribe(new Subscribe(1, 2, new Date()));
        System.out.println(subscribe);
    }

    @Test
    public void commentTest()
    {
        ContentServiceImpl contentServiceImpl = context.getBean("contentServiceImpl", ContentServiceImpl.class);
        Comment comment=new Comment();
        comment.setComment_text("20210404测试commentTest");
        comment.setComment_time(new Date());
        comment.setPhoto_id(1);
        comment.setComment_user_id(1);
        int i = contentServiceImpl.CommentPost(comment);
        System.out.println(i);
    }
    @Test
    public void ForwardTest()
    {
        ContentServiceImpl contentServiceImpl = context.getBean("contentServiceImpl", ContentServiceImpl.class);
        Forwarding forwarding=new Forwarding();
        forwarding.setForward_user_id(1);
        forwarding.setForward_text("20210404测试ForwardTest");
        forwarding.setForward_time(new Date());
        forwarding.setPhoto_id(1);
        int i = contentServiceImpl.ForwardPost(forwarding);
        System.out.println(i);
    }
}
