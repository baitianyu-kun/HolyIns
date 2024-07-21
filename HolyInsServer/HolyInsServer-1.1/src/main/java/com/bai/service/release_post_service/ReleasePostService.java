package com.bai.service.release_post_service;

import com.bai.pojo.Photo;

public interface ReleasePostService {
    //插入photos表中
    int insertIntoPhotos(Photo photo);
}
