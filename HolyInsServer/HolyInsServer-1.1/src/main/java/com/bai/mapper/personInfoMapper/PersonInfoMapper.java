package com.bai.mapper.personInfoMapper;

import com.bai.pojo.ForwardPhotoID;
import com.bai.pojo.LikePhotoID;
import com.bai.pojo.LoginInfo;
import com.bai.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PersonInfoMapper {
    //forward_user_id获取所有forwarding对象，里面有forward_id，然后去photo_forward当中获取photo_id，然后在业务层当中获取已经转发的post
    //返回的是Forward_PhotoID的list
    List<ForwardPhotoID> forwardPhotoIDs(@Param("forward_user_id") int forward_user_id);
    //like_user_id获取所有likes对象，里面包括like_id，然后去photo_like中获取photo_id，可以用来在业务层获取所有点赞了的post
    //返回的是Like_PhotoID的list
    List<LikePhotoID> likePhotoIDs(@Param("like_user_id")int like_user_id);

    //个人信息修改,前端要将其id也返回过来,返回的是影响的行数
    int changeUserInfo(UserInfo userInfo);
    //同时需要修改登录信息表中的name
    int changeLoginInfo(LoginInfo loginInfo);
}
