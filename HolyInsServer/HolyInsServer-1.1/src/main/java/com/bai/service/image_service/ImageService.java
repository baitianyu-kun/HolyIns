package com.bai.service.image_service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface ImageService {

    //storagePlace,是图片的相对路径比如/pictures/photo/
    String uploadImage(MultipartFile multipartFile,String storagePlace);
    String uploadPhoto(MultipartFile multipartFile);
    String uploadHeadPic(MultipartFile multipartFile);
    String getImage(HttpServletResponse response, String imagePath);
    String getHeadPic(HttpServletResponse response,String headPicPath);
    String getPhoto(HttpServletResponse response,String photoPath);
}
