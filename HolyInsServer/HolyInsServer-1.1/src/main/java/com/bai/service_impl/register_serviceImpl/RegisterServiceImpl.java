package com.bai.service_impl.register_serviceImpl;

import com.bai.mapper.registerMapper.RegisterMapper;
import com.bai.pojo.LoginInfo;
import com.bai.pojo.UserInfo;
import com.bai.service.register_service.RegisterService;
import com.bai.state.ActivityStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {

    private RegisterMapper registerMapper;

    @Autowired
    public void setRegisterMapper(RegisterMapper registerMapper) {
        this.registerMapper = registerMapper;
    }

    @Override
    /*
     * @Description:
     * @Param: [userInfo, loginInfo]
     * @Return: int
     * @Author: baitianyu
     * @Date: 2021/5/6 22:00
     **/

    public int Register(UserInfo userInfo, LoginInfo loginInfo) {
        //这两个sql语句影响的行数都不为0才证明插入成功
        if (registerMapper.RegisterToLoginInfo(loginInfo) != 0 && registerMapper.RegisterToUserInfo(userInfo) != 0)
            return ActivityStatus.REGISTER_SUCCESS;
        else
            return ActivityStatus.REGISTER_FAILED;
    }

    @Override
    /*
     * @Description:
     * @Param: [account]
     * @Return: java.lang.Integer
     * @Author: baitianyu
     * @Date: 2021/5/6 22:00
     **/

    public Integer IsAccountExist(String account) {
        System.out.println("registerMapper______IsAccountExist" + registerMapper.IsAccountExist(account));
        return 0;
    }

    @Override
    /*
     * @Description:
     * @Param: [e_mail]
     * @Return: java.lang.Integer
     * @Author: baitianyu
     * @Date: 2021/5/6 22:00
     **/

    public Integer IsE_mailExist(String e_mail) {
        System.out.println("registerMapper______IsemailExist" + registerMapper.IsE_mailExist(e_mail));
        return 0;
    }

}
