package com.lghcode.briefbook.service;

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
}
