package com.tang.web.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yuma on 2019/12/14.
 */
public class CommonUtil {

    private static final String default_time_format = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间转换为格式化字符串
     * @param date
     * @return
     */
    public static String transDate2String(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(default_time_format);
        return sdf.format(date);
    }

    /**
     * 添加时间
     * @param now 当前时间
     * @param minutes 分钟
     * @return
     */
    public static long transTime(Date  now, long minutes){

        long start = now.getTime();
        return start + minutes*60*1000;
    }

    public static void main(String[] args) {
        Date now = new Date();
        System.out.println(System.currentTimeMillis());
        System.out.println(transTime(now, 30));
    }
}
