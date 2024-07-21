package com.bai.mapper.releasePostMapper;

import com.bai.pojo.Photo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReleasePostMapper {
    //插入photos表中
    int insertIntoPhotos(Photo photo);
}
