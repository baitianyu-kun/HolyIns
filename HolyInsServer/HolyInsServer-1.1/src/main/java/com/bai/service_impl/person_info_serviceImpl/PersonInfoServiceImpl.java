package com.bai.service_impl.person_info_serviceImpl;

import com.bai.mapper.personInfoMapper.PersonInfoMapper;
import com.bai.pojo.*;
import com.bai.service.person_info_service.PersonInfoService;
import com.bai.service_impl.base_serviceImpl.BaseServiceImpl;
import com.bai.state.ActivityStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonInfoServiceImpl extends BaseServiceImpl implements PersonInfoService {

    private PersonInfoMapper personInfoMapper;

    @Autowired
    public void setPersonInfoMapper(PersonInfoMapper personInfoMapper) {
        this.personInfoMapper = personInfoMapper;
    }

    @Override
    public List<UserPost> findOwnPost(int now_user_id) {
        return findAllUserPostsByUserID(now_user_id);
    }

    @Override
    /*
     * @Description:
     * @Param: [now_user_id]
     * @Return: java.util.List<com.bai.pojo.UserPostForward>
     * @Author: baitianyu
     * @Date: 2021/5/6 22:00
     **/

    public List<UserPostForward> findOwnForwardingPost(int now_user_id) {
        List<UserPostForward> allOwnPostForward = new ArrayList<>();
        for (ForwardPhotoID forwardPhotoID : personInfoMapper.forwardPhotoIDs(now_user_id)) {
            //每次获取到一个forwardPhotoID，里面有forwarding这个对象和photo_id,
            // 后面根据photo_id去获取UserPost，然后把forwardPhotoID里的forwarding对象和获取到的UserPost都放入UserPostForward当中
            Forwarding userForwarding = forwardPhotoID.getForwarding();
            UserPost userForwardPost = findSpecificUserPostByPhotoID(forwardPhotoID.getPhoto_id());
            UserPostForward userPostForward = new UserPostForward(userForwardPost, userForwarding);
            allOwnPostForward.add(userPostForward);
        }
        return allOwnPostForward;
    }

    @Override
    /*
     * @Description:
     * @Param: [now_user_id]
     * @Return: java.util.List<com.bai.pojo.UserPostLike>
     * @Author: baitianyu
     * @Date: 2021/5/6 22:00
     **/

    public List<UserPostLike> findOwnLikePost(int now_user_id) {
        List<UserPostLike> allOwnPostLike = new ArrayList<>();
        for (LikePhotoID likePhotoID : personInfoMapper.likePhotoIDs(now_user_id)) {
            //与上个方法相同
            Likes userLikes = likePhotoID.getLikes();
            UserPost userLikePost = findSpecificUserPostByPhotoID(likePhotoID.getPhoto_id());
            UserPostLike userPostLike = new UserPostLike(userLikes, userLikePost);
            allOwnPostLike.add(userPostLike);
        }
        return allOwnPostLike;
    }

    @Override
    /*
     * @Description:
     * @Param: [userInfo]
     * @Return: int
     * @Author: baitianyu
     * @Date: 2021/5/6 22:00
     **/

    public int changeUserInfo(UserInfo userInfo) {
        if (personInfoMapper.changeUserInfo(userInfo) != 0) {
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setName(userInfo.getName());
            loginInfo.setAccount(userInfo.getAccount());
            if (personInfoMapper.changeLoginInfo(loginInfo) != 0) {
                return ActivityStatus.PERSON_INFO_CHANGE_SUCCESS;
            } else {
                return ActivityStatus.PERSON_INFO_CHANGE_FAILED;
            }
        } else
            return ActivityStatus.PERSON_INFO_CHANGE_FAILED;
    }

    public void mapperTest(int now_user_id) {
        //System.out.println(personInfoMapper.forwardPhotoIDs(now_user_id));
        System.out.println(personInfoMapper.likePhotoIDs(now_user_id));
    }
}
