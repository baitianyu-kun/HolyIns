package com.bai.service_impl.login_serviceImpl;

import com.bai.mapper.loginMapper.LoginMapper;
import com.bai.pojo.LoginInfo;
import com.bai.service.login_service.LoginService;
import com.bai.state.ActivityStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private LoginMapper loginMapper;

    @Autowired
    @Qualifier("loginMapper")
    public void setLoginMapper(LoginMapper loginMapper) {
        this.loginMapper = loginMapper;
    }

    @Override
    /*
     * @Description:
     * @Param: [loginInfo]
     * @Return: int
     * @Author: baitianyu
     * @Date: 2021/5/6 21:59
     **/

    public int Login(LoginInfo loginInfo) {
        String pwd = loginMapper.ReturnPwd(loginInfo);
        if (pwd == null) {
            return ActivityStatus.ACCOUNT_NOT_EXIST;
        } else {
            if (pwd.equals(loginInfo.getPassword()))
                return ActivityStatus.LOGIN_SUCCESS;
            else
                return ActivityStatus.LOGIN_FAILED;
        }
    }
}
