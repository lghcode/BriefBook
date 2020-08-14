package com.lghcode.briefbook.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lghcode.briefbook.mapper.UserFansMapper;
import com.lghcode.briefbook.model.UserFans;
import com.lghcode.briefbook.service.UserFansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:LaLion
 * @Date:2020/8/13 19:57
 */
@Service
public class UserFansServiceImpl implements UserFansService {

    @Autowired
    private UserFansMapper userFansMapper;

    /**
     * 获取某个用户关注了多少人
     *
     * @param userId 用户id
     * @return Integer
     * @Author laiyou
     * @Date 2020/8/13 23:59
     */
    @Override
    public Integer getUserAttentionCount(Long userId) {
        return userFansMapper.selectCount(new QueryWrapper<UserFans>()
                .lambda().eq(UserFans::getFansId,userId)
        );
    }

    /**
     * 获取某个用户的粉丝数量
     *
     * @param userId 用户id
     * @return Integer
     * @Author laiyou
     * @Date 2020/8/14 16:30
     */
    @Override
    public Integer getUserFansCount(Long userId) {
        return userFansMapper.selectCount(new QueryWrapper<UserFans>()
                .lambda().eq(UserFans::getUserId,userId)
        );
    }
}
