package com.bai.controller;

import com.alibaba.fastjson.JSON;
import com.bai.pojo.Post;
import com.bai.pojo.UserInfo;
import com.bai.service.base_service.BaseService;
import com.bai.service_impl.base_serviceImpl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//待定
@RestController
@RequestMapping("/base")
public class BaseController {
    private BaseService baseService;

    @Autowired
    @Qualifier("baseServiceImpl")
    public void setBaseService(BaseService baseService) {
        this.baseService = baseService;
    }

    //pass
    @PostMapping("/findAllPostsByUserID")
    public String findAllPostsByUserID(@RequestParam("user_id") int user_id) {
        return JSON.toJSONString(baseService.findAllPostsByUserID(user_id));
    }

    //pass
    @PostMapping("/findSpecificPostByPhotoID")
    public String findSpecificPostByPhotoID(@RequestParam("photo_id") int photo_id) {
        return JSON.toJSONString(baseService.findSpecificPostByPhotoID(photo_id));
    }

    @PostMapping("/findAllUserPostsByUserID")
    public String findAllUserPostsByUserID(@RequestParam("user_id") int user_id) {
        return JSON.toJSONString(baseService.findAllUserPostsByUserID(user_id));
    }

    @PostMapping("/findSpecificUserPostByPhotoID")
    public String findSpecificUserPostByPhotoID(@RequestParam("photo_id") int photo_id) {
        return JSON.toJSONString(baseService.findSpecificUserPostByPhotoID(photo_id));
    }
}
