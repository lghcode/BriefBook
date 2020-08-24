package com.lghcode.briefbook.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lghcode.briefbook.mapper.UserFansMapper;
import com.lghcode.briefbook.model.UserFans;
import com.lghcode.briefbook.model.vo.UserListVo;
import com.lghcode.briefbook.service.UserFansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 获取某个用户关注的用户列表数据
     *
     * @param userId 某个用户id
     * @return List<UserListVo>
     * @Author lghcode
     * @Date 2020/8/24 8:15
     */
    @Override
    public List<UserListVo> getUserFollow(Long userId) {
        return userFansMapper.getUserFollow(userId);
    }

    /**
     * 获取某个用户的粉丝用户列表数据
     *
     * @param userId 某个用户id
     * @return List<UserListVo>
     * @Author lghcode
     * @Date 2020/8/24 8:15
     */
    @Override
    public List<UserListVo> getUserFans(Long userId) {
        return userFansMapper.getUserFans(userId);
    }
}
