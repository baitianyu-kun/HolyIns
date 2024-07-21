package com.bai.mapper.findpswMapper;

import com.bai.pojo.LoginInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FindPswMapper {
    int FindPsw(LoginInfo loginInfo);
    String ReturnUserName(String E_mail);
}
