package com.lghcode.briefbook.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lghcode.briefbook.model.UserArticle;
import com.lghcode.briefbook.model.vo.ArticleVo;
import com.lghcode.briefbook.model.vo.PraiseUserVo;
import com.lghcode.briefbook.model.vo.UserBaseVo;

import java.util.List;

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

    /**
     * 根据文章id获取文章发布作者
     * @param articleId 文章id
     * @return UserBaseVo
     */
    UserBaseVo getUserByArticleId(Long articleId);

    /**
     * 获取文章的赞赏用户列表数据
     *
     * @Author lghcode
     * @param  articleId 文章id
     * @return List<PraiseUserVo>
     * @Date 2020/8/20 22:37
     */
    List<PraiseUserVo> getPraiseUserListByArticleId(Long articleId);

    /**
     * 获取用户的文章列表数据
     *
     * @param userId 用户id
     * @return List<ArticleVo>
     * @Author lghcode
     * @Date 2020/8/23 14:59
     */
    List<ArticleVo> getUserArticles(Long userId);
}
