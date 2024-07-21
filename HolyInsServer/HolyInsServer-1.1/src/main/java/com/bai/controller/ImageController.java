package com.bai.controller;

import com.bai.service.image_service.ImageService;
import com.bai.state.ActivityStatus;
import com.bai.state.StorageState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/image")
@RestController
public class ImageController {

    private ImageService imageService;

    @Autowired
    @Qualifier("imageServiceImpl")
    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/getHeadPic")
    public String getHeadPic(HttpServletResponse response, @RequestParam("headPicPath") String headPicPath) {
        return imageService.getHeadPic(response, headPicPath);
    }

    @GetMapping("/getPhoto")
    public String getPhoto(HttpServletResponse response, @RequestParam("photoPath") String photoPath) {
        //Already
        return imageService.getPhoto(response, photoPath);
    }

    @PostMapping("/uploadHeadPic")
    public String uploadHeadPic(@RequestParam("headpic") MultipartFile multipartFile) {
        return imageService.uploadHeadPic(multipartFile);
    }

    @PostMapping("/uploadPhoto")
    public String uploadPhoto(@RequestParam("photo") MultipartFile multipartFile) {
        return imageService.uploadPhoto(multipartFile);
    }
}
