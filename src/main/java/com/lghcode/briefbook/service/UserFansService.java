package com.lghcode.briefbook.service;

import com.lghcode.briefbook.model.vo.UserListVo;

import java.util.List;

/**
 * @Author:LaLion
 * @Date:2020/8/13 19:56
 */
public interface UserFansService {

    /**
     * 获取某个用户关注了多少人
     *
     * @Author laiyou
     * @param  userId 用户id
     * @return Integer
     * @Date 2020/8/13 23:59
     */
    Integer getUserAttentionCount(Long userId);

    /**
     * 获取某个用户的粉丝数量
     *
     * @Author laiyou
     * @param  userId 用户id
     * @return Integer
     * @Date 2020/8/14 16:30
     */
    Integer getUserFansCount(Long userId);

    /**
     * 获取某个用户关注的用户列表数据
     *
     * @Author lghcode
     * @param  userId 某个用户id
     * @return List<UserListVo>
     * @Date 2020/8/24 8:15
     */
    List<UserListVo> getUserFollow(Long userId);

    /**
     * 获取某个用户的粉丝用户列表数据
     *
     * @Author lghcode
     * @param  userId 某个用户id
     * @return List<UserListVo>
     * @Date 2020/8/24 8:15
     */
    List<UserListVo> getUserFans(Long userId);
}
