package com.bai.service.person_info_service;

import com.bai.pojo.*;

import java.util.List;

public interface PersonInfoService {

    //获取自己的帖子
    List<UserPost> findOwnPost(int now_user_id);

    //获取自己转发的帖子和转发信息
    List<UserPostForward> findOwnForwardingPost(int now_user_id);

    //获取自己喜欢的帖子和like信息,比如啥时候like的
    List<UserPostLike> findOwnLikePost(int now_user_id);

    int changeUserInfo(UserInfo userInfo);
}
