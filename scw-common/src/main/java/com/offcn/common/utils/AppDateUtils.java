package com.offcn.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// 格式化输出时间日期
public class AppDateUtils {

    public static String getFormartTime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }

    public static String getFormartTime(String pattern){
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(new Date());
    }

    public static String getFormartTime(Date date,String pattern){
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }
}
