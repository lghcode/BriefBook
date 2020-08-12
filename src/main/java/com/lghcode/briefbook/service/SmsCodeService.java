package com.lghcode.briefbook.service;

import com.lghcode.briefbook.model.SmsCode;

import java.util.Date;

/**
 * @Author lgh
 * @Date 2020/8/11 12:33
 */
public interface SmsCodeService {

    /**
     * 新增短信验证码
     *
     * @Author lghcode
     * @param  smsCode 短信验证码实体
     * @Date 2020/8/11 12:45
     */
    void save(SmsCode smsCode);

    /**
     * 检查是否在1分钟以内重复发送短信验证码
     *
     * @Author lghcode
     * @param  mobile 手机号
     * @param  type 短信验证码类型
     * @param  oneMintueBefore 当前时间之前的一分钟时间
     * @param  nowDate 当前时间
     * @return boolean
     * @Date 2020/8/11 13:02
     */
    boolean checkRepeatSendSms(String mobile, int type, Date oneMintueBefore, Date nowDate);

    /**
     * 根据用户手机号和type去查询验证码记录
     *
     * @Author lghcode
     * @param  mobile 手机号
     * @param  type 验证码发送类型
     * @return SmsCode
     * @Date 2020/8/12 16:32
     */
    SmsCode getByMobileAndType(String mobile, Integer type);
}
