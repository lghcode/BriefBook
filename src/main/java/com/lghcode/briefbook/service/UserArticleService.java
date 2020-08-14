package com.lghcode.briefbook.service;

/**
 * @Author:LaLion
 * @Date:2020/8/1320:29
 */
public interface UserArticleService {


    /**
     * 获取当前用户发布的私密文章数量
     *
     * @Author laiyou
     * @param  userId 用户id
     * @return Integer
     * @Date 2020/8/14 16:34
     */
    Integer getUserPrivateArticleCount(Long userId);

    /**
     * 获取某个用户发布的公开文章的总字数
     *
     * @Author laiyou
     * @param  userId 用户id
     * @return Integer
     * @Date 2020/8/14 16:50
     */
    Integer getUserWordCount(Long userId);

    /**
     * 获取某个用户收获文章赞的总数
     *
     * @Author laiyou
     * @param  userId 用户id
     * @return Integer
     * @Date 2020/8/14 17:02
     */
    Integer getUserApprovalCount(Long userId);
}
