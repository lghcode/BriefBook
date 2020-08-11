package com.lghcode.briefbook.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lghcode.briefbook.model.SmsCode;
import org.apache.ibatis.annotations.Param;

/**
 * @Author lgh
 * @Date 2020/8/11 12:32
 */
public interface SmsCodeMapper extends BaseMapper<SmsCode> {

    SmsCode selectByUserIdAndType(@Param("userId") Long userId, @Param("type") Integer type);
}
