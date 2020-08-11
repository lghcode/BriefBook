package com.lghcode.briefbook.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 日期工具类
 * @Author lgh
 * @Date 2020/8/11 12:55
 */
public class DateUtil {

    /**
     * 获取当前时间的前一分钟时间
     *
     * @Author lghcode
     * @return Date
     * @Date 2020/8/11 18:27
     */
    public static Date getOneMintueBefore(){
        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(Calendar.MINUTE, -1);
        return beforeTime.getTime();
    }

    /***
     * 判断字符串是否是yyyy-MM-dd格式
     * @param text 待校验的字符串
     * @return boolean 是否是日期格式
     */
    public static boolean isRqFormat(String text){
        String regex = "((19|20)[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])";
        if(StringUtils.isBlank(text) || StringUtils.isBlank(regex)){
            return false;
        }
        return Pattern.compile(regex).matcher(text).matches();

    }
}
