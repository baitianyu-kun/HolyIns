package com.bai.service.search_service;

import com.bai.pojo.Post;
import com.bai.pojo.UserInfo;
import com.bai.pojo.UserPost;

import java.util.List;
import java.util.Map;

/*
写了两个函数的话就是返回值不一样
 */
public interface SearchService {
    //查询某个user
    UserInfo findUser(Map<String, String> map);

    //根据id查找user
    UserInfo findUserByID(String user_id);

    //根据账号查找user
    UserInfo findUserByAccount(String user_account);

    //根据email查找user
    UserInfo findUserByEmail(String user_email);

    //根据姓名模糊查找
    List<UserInfo> findUserByName(String user_name);

    //混合式查找用户，集合了姓名、email和账号
    List<UserInfo> findUserByMixture(String search_condition);

    //查询好多user
    List<UserInfo> findUsers(Map<String, List<String>> listMap);

    //根据很多id批量查询user
    List<UserInfo> findUsersByIDs(List<Integer> user_ids);

    //根据照片描述查询
    List<Post> findPostByPhotoDescribe(String photo_describe);

    List<UserPost> findUserPostByPhotoDescribe(String photo_describe);

    //根据照片所带hashtag查询
    List<Post> findPostByHashTag(String hashtag_text);

    List<UserPost> findUserPostByHashTag(String hashtag_text);

    //根据hashtag_text去寻找hashtag_id
    List<Integer> findHashTagID(String hashtag_text);

    //根据hashtag_id去找photo_id
    List<Integer> findPhotoIDByHashTagID(Map<String, List<Integer>> hashtagIDMap);
}
