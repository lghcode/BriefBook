package com.lghcode.briefbook.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 日期工具类
 * @Author lgh
 * @Date 2020/8/11 12:55
 */
public class CommonUtil {

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

    /**
     * 判断字符串是否是手机号格式
     *
     * @Author lghcode
     * @param  mobiles 要判断的字符串
     * @return boolean
     * @Date 2020/8/12 16:23
     */
    public static boolean isMobile(String mobiles) {
        String telRegex = "[1][3578]\\d{9}";
        // "[1]"代表第1位为数字1，"[3578]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else {
            return mobiles.matches(telRegex);
        }
    }
}
