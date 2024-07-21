package com.bai.HolyIns.utils;

public class IntegerUtils {
    /**
     * @Description:
     * @Param: [len] 生成数字的位数
     * @Return: int
     * @Author: baitianyu
     * @Date: 2021/3/22 10:19
     **/
    public static String GenerateAnyLenRandomNumber(int len) {
        long rs = (long) ((Math.random() * 9 + 1) * Math.pow(10, len - 1));
        return String.valueOf(rs);
    }
    public static String GenerateSixAccount(){
        return GenerateAnyLenRandomNumber(6);
    }
}
