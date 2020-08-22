package com.lghcode.briefbook.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * @Author:LaLion
 * @Date:2020/8/1320:29
 */
@Transactional(rollbackFor = Exception.class)
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

    /**
     * 点赞/取消点赞  文章
     *
     * @Author lghcode
     * @param userId 当前登录用户id
     * @param articleId 文章id
     * @param type 0-点赞，1-取消点赞
     * @Date 2020/8/22 11:00
     */
    void userLikeArticle(Long userId, Long articleId, Integer type);

    /**
     * 收藏/取消收藏  文章
     *
     * @Author lghcode
     * @param userId 当前登录用户id
     * @param articleId 文章id
     * @param type 0-收藏，1-取消收藏
     * @Date 2020/8/22 11:00
     */
    void userCollectArticle(Long userId, Long articleId, Integer type);

    /**
     * 赞赏/取消赞赏  文章
     *
     * @Author lghcode
     * @param userId 当前登录用户id
     * @param diamond 简钻数量
     * @param articleId 文章id
     * @Date 2020/8/22 11:00
     */
    void userPraiseArticle(Long userId, String diamond, Long articleId);
}
