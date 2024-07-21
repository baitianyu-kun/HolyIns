package com.bai.service.recommend_service;

import com.bai.pojo.UserPost;

import java.util.List;

public interface RecommendService {
    //根据当前user_id获取推荐的post
    List<UserPost> getRecommendPost(int now_user_id);

}
