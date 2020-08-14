package com.lghcode.briefbook.service.impl;

import com.lghcode.briefbook.mapper.UserArticleMapper;
import com.lghcode.briefbook.service.UserArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:LaLion
 * @Date:2020/8/1320:30
 * @Version 1.0
 */
@Service
public class UserArticleServiceImpl implements UserArticleService {

    @Autowired
    private UserArticleMapper userArticleMapper;


    /**
     * 获取当前用户发布的私密文章数量
     *
     * @param userId 用户id
     * @return Integer
     * @Author laiyou
     * @Date 2020/8/14 16:34
     */
    @Override
    public Integer getUserPrivateArticleCount(Long userId) {
        return userArticleMapper.getUserPrivateArticleCount(userId);
    }

    /**
     * 获取某个用户发布的公开文章的总字数
     *
     * @param userId 用户id
     * @return Integer
     * @Author laiyou
     * @Date 2020/8/14 16:50
     */
    @Override
    public Integer getUserWordCount(Long userId) {
        return userArticleMapper.getUserWordCount(userId);
    }

    /**
     * 获取某个用户收获文章赞的总数
     *
     * @param userId 用户id
     * @return Integer
     * @Author laiyou
     * @Date 2020/8/14 17:02
     */
    @Override
    public Integer getUserApprovalCount(Long userId) {
        return userArticleMapper.getUserApprovalCount(userId);
    }
}
