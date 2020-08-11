package com.lghcode.briefbook.service.impl;

import cn.hutool.core.util.ObjectUtil;
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
    public void save(SmsCode smsCode) {
        SmsCode resCode = smsCodeMapper.selectOne(new QueryWrapper<SmsCode>().lambda()
        .eq(SmsCode::getMobile,smsCode.getMobile())
        .eq(SmsCode::getType,smsCode.getType()));
        if (ObjectUtil.isEmpty(resCode)){
            //新增记录
            smsCodeMapper.insert(smsCode);
        }else{
            //更新记录
            resCode.setCode(smsCode.getCode());
            resCode.setCreateTime(new Date());
            smsCodeMapper.updateById(resCode);
        }
    }

    @Override
    public boolean checkRepeatSendSms(String mobile, int type, Date oneMintueBefore, Date nowDate) {
        SmsCode smsCode = smsCodeMapper.selectOne(new QueryWrapper<SmsCode>().lambda().
                eq(SmsCode::getMobile,mobile).
                eq(SmsCode::getType,type).
                between(SmsCode::getCreateTime,oneMintueBefore,nowDate));
        return smsCode != null;
    }

    /**
     * 根据用户id和type去查询单个用户
     *
     * @param userId 用户id
     * @param type   验证码发送类型
     * @return SmsCode
     * @Author laiyou
     * @Date 2020/8/11 17:53
     */
    @Override
    public SmsCode getByUserIdAndType(Long userId, Integer type) {
        return smsCodeMapper.selectByUserIdAndType(userId,type);
    }
}
