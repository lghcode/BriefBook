package com.lghcode.briefbook.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lghcode.briefbook.mapper.SmsCodeMapper;
import com.lghcode.briefbook.model.SmsCode;
import com.lghcode.briefbook.service.SmsCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author lgh
 * @Date 2020/8/11 12:33
 */
@Service
public class SmsCodeServiceImpl implements SmsCodeService {

    @Autowired
    private SmsCodeMapper smsCodeMapper;


    @Override
    public void insert(SmsCode smsCode) {
        smsCodeMapper.insert(smsCode);
    }

    @Override
    public boolean checkRepeatSendSms(String mobile, int type, Date oneMintueBefore, Date nowDate) {
        SmsCode smsCode = smsCodeMapper.selectOne(new QueryWrapper<SmsCode>().lambda().
                eq(SmsCode::getMobile,mobile).
                eq(SmsCode::getType,type).
                between(SmsCode::getCreateTime,oneMintueBefore,nowDate));
        return smsCode != null;
    }
}
