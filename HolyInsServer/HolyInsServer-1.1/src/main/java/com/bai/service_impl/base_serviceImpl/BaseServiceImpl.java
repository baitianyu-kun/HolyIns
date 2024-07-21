package com.bai.service_impl.base_serviceImpl;

import com.bai.mapper.baseMapper.BaseMapper;
import com.bai.pojo.Post;
import com.bai.pojo.PostSmall;
import com.bai.pojo.UserInfo;
import com.bai.pojo.UserPost;
import com.bai.service.base_service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BaseServiceImpl implements BaseService {

    private BaseMapper baseMapper;

    @Autowired
    public void setBaseMapper(BaseMapper baseMapper) {
        this.baseMapper = baseMapper;
    }

    @Override
    /*
     * @Description:
     * @Param: [user_id]
     * @Return: java.util.List<com.bai.pojo.Post>
     * @Author: baitianyu
     * @Date: 2021/5/6 21:56
     **/

    public List<Post> findAllPostsByUserID(int user_id) {
        return baseMapper.findAllPostsByUserID(user_id);
    }

    @Override
    /*
     * @Description:
     * @Param: [photo_id]
     * @Return: com.bai.pojo.Post
     * @Author: baitianyu
     * @Date: 2021/5/6 21:56
     **/

    public Post findSpecificPostByPhotoID(int photo_id) {
        return baseMapper.findSpecificPostByPhotoID(photo_id);
    }

    @Override
    /*
     * @Description:
     * @Param: [user_id]
     * @Return: java.util.List<com.bai.pojo.UserPost>
     * @Author: baitianyu
     * @Date: 2021/5/6 21:56
     **/

    public List<UserPost> findAllUserPostsByUserID(int user_id) {
        return baseMapper.findAllUserPostsByUserID(user_id);
    }

    @Override
    /*
     * @Description:
     * @Param: [photo_id]
     * @Return: com.bai.pojo.UserPost
     * @Author: baitianyu
     * @Date: 2021/5/6 21:56
     **/

    public UserPost findSpecificUserPostByPhotoID(int photo_id) {
        return baseMapper.findSpecificUserPostByPhotoID(photo_id);
    }


}
