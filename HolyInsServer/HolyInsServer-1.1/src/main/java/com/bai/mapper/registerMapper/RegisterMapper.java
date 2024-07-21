package com.bai.mapper.registerMapper;

import com.bai.pojo.LoginInfo;
import com.bai.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RegisterMapper {
    //将注册信息写入userinfo
    int RegisterToUserInfo(UserInfo userInfo);

    //将注册信息写入logininfo
    int RegisterToLoginInfo(LoginInfo loginInfo);

    /*
    有时候为了提高效率，只是为了测试下某个表中是否存在记录，就用1来代替。
    例如我的student 中 有学生信息表，我只想知道里面有没有数据并不在乎数据是哪些，就可以
    select 1 from student ，这样大大提高查询速度，选出100行个1，说明有100条学生信息。
    这里为啥要用Integer呢，就是因为啊，select 1 的返回结果要么是null，要么是1，又因为int没有null，其初值是0，没有null，所以如果查到null的话就没办法转换了，所以这里使用integer
     */
    Integer IsAccountExist(@Param("Account") String account);

    Integer IsE_mailExist(@Param("E_mail") String e_mail);

}
