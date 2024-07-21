package com.bai.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {
    @Test
    public void tetst() {
        System.out.println(new Date());
    }

    public static String getLocalDate() {
        LocalDate date = LocalDate.now(); // 获取当前日期
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return date.format(formatter);
    }

    public static String getLocalDate_AND_Time() {
        LocalDateTime date = LocalDateTime.now(); // 获取当前日期
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return date.format(formatter);
    }

    public static String getLocalYear() {
        LocalDate date = LocalDate.now(); // get the current year
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        return date.format(formatter);
    }

    public static String getLocalMonth() {
        LocalDate date = LocalDate.now(); // get the current month
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");
        return date.format(formatter);
    }

    public static String getLocalDay() {
        LocalDate date = LocalDate.now(); // get the current day
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
        return date.format(formatter);
    }
}
