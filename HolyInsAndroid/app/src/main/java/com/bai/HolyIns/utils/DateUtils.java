package com.bai.HolyIns.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {
    //把日期转为字符串  
    public static String convertToString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        return df.format(date);
    }
}
