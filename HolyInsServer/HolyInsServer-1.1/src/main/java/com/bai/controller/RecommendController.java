package com.bai.controller;

import com.alibaba.fastjson.JSON;
import com.bai.service.recommend_service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recommend")
public class RecommendController {

    private RecommendService recommendService;

    @Autowired
    public void setRecommendService(RecommendService recommendService) {
        this.recommendService = recommendService;
    }

    @PostMapping("/getRecommendPost")
    public String getRecommendPost(@RequestParam("now_user_id") int now_user_id) {
        return JSON.toJSONString(recommendService.getRecommendPost(now_user_id));
    }
}
