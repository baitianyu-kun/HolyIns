package com.bai.service.findpsw_service;

import com.bai.mapper.findpswMapper.FindPswMapper;
import com.bai.pojo.LoginInfo;

public interface FindPswService {
    int FindPsw(LoginInfo loginInfo);
    String ReturnUserName(String E_mail);
}
