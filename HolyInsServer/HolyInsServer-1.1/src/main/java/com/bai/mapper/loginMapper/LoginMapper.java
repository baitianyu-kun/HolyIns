package com.bai.mapper.loginMapper;

import com.bai.pojo.LoginInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {
    //根据账号或者邮箱，返回密码判断是否正确
    String ReturnPwd(LoginInfo loginInfo);
}
