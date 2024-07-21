package com.bai.mapper.baseMapper;

import com.bai.pojo.Post;
import com.bai.pojo.PostSmall;
import com.bai.pojo.UserInfo;
import com.bai.pojo.UserPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface BaseMapper {

    //根据这个人的id查询所有的帖子
    List<Post> findAllPostsByUserID(@Param("user_id")int user_id);

    //查询某一张帖子
    Post findSpecificPostByPhotoID(@Param("photo_id") int photo_id);

    //根据这个人的id查询所有的帖子
    List<UserPost> findAllUserPostsByUserID(@Param("user_id") int user_id);

    //查询某一张帖子
    UserPost findSpecificUserPostByPhotoID(@Param("photo_id") int photo_id);

}
