package com.bai.service_impl.release_post_serviceImpl;

import com.bai.mapper.releasePostMapper.ReleasePostMapper;
import com.bai.pojo.Photo;
import com.bai.service.release_post_service.ReleasePostService;
import com.bai.state.ActivityStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReleasePostServiceImpl implements ReleasePostService {

    private ReleasePostMapper releasePostMapper;

    @Autowired
    public void setReleasePostMapper(ReleasePostMapper releasePostMapper) {
        this.releasePostMapper = releasePostMapper;
    }

    @Override
    /*
     * @Description:
     * @Param: [photo]
     * @Return: int
     * @Author: baitianyu
     * @Date: 2021/5/6 22:02
     **/

    public int insertIntoPhotos(Photo photo) {
        if (releasePostMapper.insertIntoPhotos(photo) != 0)
            return ActivityStatus.RELEASE_POST_SUCCESS;
        else
            return ActivityStatus.RELEASE_POST_FAILED;
    }
}
