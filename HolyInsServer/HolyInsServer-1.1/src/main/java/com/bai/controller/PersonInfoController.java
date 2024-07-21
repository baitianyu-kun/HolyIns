package com.bai.controller;

import com.alibaba.fastjson.JSON;
import com.bai.pojo.UserInfo;
import com.bai.service.person_info_service.PersonInfoService;
import com.bai.state.ActivityStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/personInfo")
public class PersonInfoController {

    private PersonInfoService personInfoService;

    @Autowired
    public void setPersonInfoService(PersonInfoService personInfoService) {
        this.personInfoService = personInfoService;
    }

    @PostMapping("/findOwnPost")
    public String findOwnPost(@RequestParam("now_user_id")int now_user_id){
        return JSON.toJSONString(personInfoService.findOwnPost(now_user_id));
    }

    @PostMapping("/findOwnLikePost")
    public String findOwnLikePost(@RequestParam("now_user_id")int now_user_id){
        return JSON.toJSONString(personInfoService.findOwnLikePost(now_user_id));
    }

    @PostMapping("/findOwnForwardingPost")
    public String findOwnForwardingPost(@RequestParam("now_user_id")int now_user_id){
        return JSON.toJSONString(personInfoService.findOwnForwardingPost(now_user_id));
    }

    @PostMapping("/changeUserInfo")
    public String changeUserInfo(@RequestParam("user_info") String JSON_UserInfo){
        /*UserInfo userInfo=new UserInfo();
        userInfo.setHeadPicturePath("111");
        userInfo.setUser_id(1);
        userInfo.setHeadPictureSize("200");
        userInfo.setName("bai");
        userInfo.setRegisterTime(new Date());
        userInfo.setSex("nan");
        userInfo.setAccount("159");
        userInfo.setE_mail("174");
        JSON_UserInfo=JSON.toJSONString(userInfo);*/

        UserInfo userInfo=JSON.parseObject(JSON_UserInfo,UserInfo.class);
        System.err.println("===========head_pic_path="+userInfo.getHeadPicturePath());
        if (personInfoService.changeUserInfo(JSON.parseObject(JSON_UserInfo, UserInfo.class))== ActivityStatus.PERSON_INFO_CHANGE_SUCCESS){
            return ActivityStatus.STRING_PERSON_INFO_CHANGE_SUCCESS;
        }
        else{
            return ActivityStatus.STRING_PERSON_INFO_CHANGE_FAILED;
        }
    }
}
