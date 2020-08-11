package com.lghcode.briefbook.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * @Author lgh
 * @Date 2020/8/11 12:55
 */
public class DateUtil {

    public static Date getOneMintueBefore(){
        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(Calendar.MINUTE, -1);
        return beforeTime.getTime();
    }

}
