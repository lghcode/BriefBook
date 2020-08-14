package com.lghcode.briefbook.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lghcode.briefbook.model.UserArticle;

/**
 * @Author:LaLion
 * @Date:2020/8/1320:25
 * @Version 1.0
 * @todo
 */
public interface UserArticleMapper extends BaseMapper<UserArticle> {


    /**
     * 获取当前用户发布的私密文章数量
     *
     * @param userId 用户id
     * @return Integer
     * @Author laiyou
     * @Date 2020/8/14 16:34
     */
    Integer getUserPrivateArticleCount(Long userId);

    /**
     * 获取某个用户发布的公开文章的总字数
     *
     * @param userId 用户id
     * @return Integer
     * @Author laiyou
     * @Date 2020/8/14 16:50
     */
    Integer getUserWordCount(Long userId);

    /**
     * 获取某个用户收获文章赞的总数
     *
     * @param userId 用户id
     * @return Integer
     * @Author laiyou
     * @Date 2020/8/14 17:02
     */
    Integer getUserApprovalCount(Long userId);
}
