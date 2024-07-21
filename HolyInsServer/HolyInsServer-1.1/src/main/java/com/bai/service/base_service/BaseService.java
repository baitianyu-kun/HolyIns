package com.bai.service.base_service;

import com.bai.pojo.Post;
import com.bai.pojo.PostSmall;
import com.bai.pojo.UserInfo;
import com.bai.pojo.UserPost;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BaseService {
    //Deprecated
    //根据这个人的id查询所有的帖子
    List<Post> findAllPostsByUserID(int user_id);
    //Deprecated
    //查询某一张帖子
    Post findSpecificPostByPhotoID(int photo_id);

    //根据这个人的id查询所有的帖子
    List<UserPost> findAllUserPostsByUserID(int user_id);

    //查询某一张帖子
    UserPost findSpecificUserPostByPhotoID(int photo_id);
}
