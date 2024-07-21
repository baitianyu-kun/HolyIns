package com.bai.controller;

import com.alibaba.fastjson.JSON;
import com.bai.pojo.Photo;
import com.bai.service.release_post_service.ReleasePostService;
import com.bai.state.ActivityStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/release")
public class ReleasePostController {

    private ReleasePostService releasePostService;

    @Autowired
    public void setReleasePostService(ReleasePostService releasePostService) {
        this.releasePostService = releasePostService;
    }

    @PostMapping("/releasePost")
    public String releasePost(@RequestParam("photo") String JSON_Photo) {
        Photo photo = JSON.parseObject(JSON_Photo, Photo.class);
        if (releasePostService.insertIntoPhotos(photo) == ActivityStatus.RELEASE_POST_SUCCESS)
            return ActivityStatus.STRING_RELEASE_POST_SUCCESS;
        else
            return ActivityStatus.STRING_RELEASE_POST_FAILED;
    }

}
