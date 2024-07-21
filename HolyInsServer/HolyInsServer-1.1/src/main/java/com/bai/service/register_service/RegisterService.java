package com.bai.service.register_service;

import com.bai.pojo.LoginInfo;
import com.bai.pojo.UserInfo;
import org.apache.ibatis.annotations.Param;

public interface RegisterService {
    int Register(UserInfo userInfo, LoginInfo loginInfo);
    Integer IsAccountExist(String account);
    Integer IsE_mailExist(String e_mail);
}
