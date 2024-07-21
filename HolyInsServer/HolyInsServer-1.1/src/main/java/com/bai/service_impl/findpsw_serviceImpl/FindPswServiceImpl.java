package com.bai.service_impl.findpsw_serviceImpl;

import com.bai.mapper.findpswMapper.FindPswMapper;
import com.bai.pojo.LoginInfo;
import com.bai.service.findpsw_service.FindPswService;
import com.bai.state.ActivityStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindPswServiceImpl implements FindPswService {

    private FindPswMapper findPswMapper;

    @Autowired
    public void setFindPswMapper(FindPswMapper findPswMapper) {
        this.findPswMapper = findPswMapper;
    }

    @Override
    /*
     * @Description:
     * @Param: [loginInfo]
     * @Return: int
     * @Author: baitianyu
     * @Date: 2021/5/6 21:59
     **/

    public int FindPsw(LoginInfo loginInfo) {
        if (findPswMapper.FindPsw(loginInfo) != 0)
            return ActivityStatus.PASSWORD_CHANGE_SUCCESS;
        else
            return ActivityStatus.PASSWORD_CHANGE_FAILED;
    }

    //如果没查到该email下的人的话，就返回该email不存在
    @Override
    /*
     * @Description:
     * @Param: [E_mail]
     * @Return: java.lang.String
     * @Author: baitianyu
     * @Date: 2021/5/6 21:59
     **/

    public String ReturnUserName(String E_mail) {
        if (findPswMapper.ReturnUserName(E_mail) == null)
            return ActivityStatus.STRING_E_MAIL_NOT_EXIST;
        else
            return findPswMapper.ReturnUserName(E_mail);
    }
}
