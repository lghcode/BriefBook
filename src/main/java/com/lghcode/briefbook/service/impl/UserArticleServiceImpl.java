package com.lghcode.briefbook.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lghcode.briefbook.enums.ArticleEnum;
import com.lghcode.briefbook.enums.UserActionEnum;
import com.lghcode.briefbook.enums.UserEnum;
import com.lghcode.briefbook.exception.BizException;
import com.lghcode.briefbook.mapper.ArticleMapper;
import com.lghcode.briefbook.mapper.UserArticleMapper;
import com.lghcode.briefbook.mapper.UserMapper;
import com.lghcode.briefbook.model.Article;
import com.lghcode.briefbook.model.User;
import com.lghcode.briefbook.model.UserArticle;
import com.lghcode.briefbook.model.vo.ArticleVo;
import com.lghcode.briefbook.service.UserActionService;
import com.lghcode.briefbook.service.UserArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author:LaLion
 * @Date:2020/8/1320:30
 * @Version 1.0
 */
@Service
public class UserArticleServiceImpl implements UserArticleService {

    @Autowired
    private UserArticleMapper userArticleMapper;

    @Autowired
    private UserActionService userActionService;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserMapper userMapper;

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

    /**
     * 点赞/取消点赞  文章
     *
     * @param userId    当前登录用户id
     * @param articleId 文章id
     * @param type      0-点赞，1-取消点赞
     * @Author lghcode
     * @Date 2020/8/22 11:00
     */
    @Override
    public void userLikeArticle(Long userId, Long articleId, Integer type) {
        //点赞文章
        if (type == UserEnum.FOLLOW.getCode()) {
            //判断有没有点过赞
            Integer count = userArticleMapper.selectCount(new QueryWrapper<UserArticle>().lambda()
                                        .eq(UserArticle::getUserId,userId)
                                        .eq(UserArticle::getArticleId,articleId)
                                        .eq(UserArticle::getType,UserEnum.USER_LIKE_ARTICLE.getCode()));
            if (count > 0) {
                throw new BizException("对该文章已经点赞过");
            }
            //点赞操作
            UserArticle userArticle = UserArticle.builder().userId(userId).articleId(articleId)
                            .type(UserEnum.USER_LIKE_ARTICLE.getCode()).createTime(new Date()).build();
            userArticleMapper.insert(userArticle);
            //每次点赞给文章增加0.002个简钻
            Article article = articleMapper.selectById(articleId);
            BigDecimal diamondCount = article.getDiamondCount();
            diamondCount = diamondCount.add(new BigDecimal("0.002"));
            article.setDiamondCount(diamondCount);
            article.setIsSettle(ArticleEnum.NOT_SETTLE.getCode());
            articleMapper.updateById(article);
            //同步到用户动态表
            userActionService.newAction(userId,UserActionEnum.LIKE.getCode(),articleId,UserActionEnum.ARTICLE.getCode());
        }else if(type == UserEnum.CANNEL_FOLLOW.getCode()) {
            //取消点赞
            userArticleMapper.delete(new QueryWrapper<UserArticle>().lambda()
                    .eq(UserArticle::getUserId,userId)
                    .eq(UserArticle::getArticleId,articleId)
                    .eq(UserArticle::getType,UserEnum.USER_LIKE_ARTICLE.getCode()));
            //每次取消点赞给文章减少0.002个简钻
            Article article = articleMapper.selectById(articleId);
            BigDecimal diamondCount = article.getDiamondCount();
            //判断文章简钻是否大于0
            int res = diamondCount.compareTo(new BigDecimal("0"));
            if (res > 0){
                diamondCount = diamondCount.subtract(new BigDecimal("0.002"));
                article.setDiamondCount(diamondCount);
                article.setIsSettle(ArticleEnum.NOT_SETTLE.getCode());
                articleMapper.updateById(article);
            }
            //同步到用户动态表
            userActionService.cannelAction(userId,UserActionEnum.LIKE.getCode(),articleId);
        }
    }

    /**
     * 收藏/取消收藏  文章
     *
     * @param userId    当前登录用户id
     * @param articleId 文章id
     * @Author lghcode
     * @Date 2020/8/22 11:00
     */
    @Override
    public void userCollectArticle(Long userId, Long articleId, Integer type) {
        //收藏文章
        if (type == UserEnum.FOLLOW.getCode()) {
            //判断有没有收藏过
            Integer count = userArticleMapper.selectCount(new QueryWrapper<UserArticle>().lambda()
                    .eq(UserArticle::getUserId,userId)
                    .eq(UserArticle::getArticleId,articleId)
                    .eq(UserArticle::getType,UserEnum.USER_COLLECT_ARTICLE.getCode()));
            if (count > 0) {
                throw new BizException("对该文章已经收藏过");
            }
            //收藏操作
            UserArticle userArticle = UserArticle.builder().userId(userId).articleId(articleId)
                    .type(UserEnum.USER_COLLECT_ARTICLE.getCode()).createTime(new Date()).build();
            userArticleMapper.insert(userArticle);
            //同步到用户动态表
            userActionService.newAction(userId,UserActionEnum.COLLECT.getCode(),articleId, UserActionEnum.ARTICLE.getCode());
        }else if(type == UserEnum.CANNEL_FOLLOW.getCode()) {
            //取消收藏
            userArticleMapper.delete(new QueryWrapper<UserArticle>().lambda()
                    .eq(UserArticle::getUserId,userId)
                    .eq(UserArticle::getArticleId,articleId)
                    .eq(UserArticle::getType,UserEnum.USER_COLLECT_ARTICLE.getCode()));
            //同步到用户动态表
            userActionService.cannelAction(userId,UserActionEnum.COLLECT.getCode(),articleId);
        }
    }

    /**
     * 赞赏/取消赞赏  文章
     *
     * @param userId    当前登录用户id
     * @param diamond   简钻数量
     * @param articleId 文章id
     * @Author lghcode
     * @Date 2020/8/22 11:00
     */
    @Override
    public void userPraiseArticle(Long userId, String diamond, Long articleId) {
        User user = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getId,userId));
        int res = user.getDiamondTotal().compareTo(new BigDecimal(diamond));
        if (res < 0){
            throw new BizException("用户简钻余额不足");
        }
        //判断有没有赞赏过
        Integer count = userArticleMapper.selectCount(new QueryWrapper<UserArticle>().lambda()
                .eq(UserArticle::getUserId,userId)
                .eq(UserArticle::getArticleId,articleId)
                .eq(UserArticle::getType,UserEnum.USER_PRAISE_ARTICLE.getCode()));
        if (count > 0) {
            throw new BizException("对该文章已经赞赏过");
        }
        //赞赏操作
        UserArticle userArticle = UserArticle.builder().userId(userId).articleId(articleId)
                .type(UserEnum.USER_PRAISE_ARTICLE.getCode()).createTime(new Date()).build();
        userArticleMapper.insert(userArticle);
        //增加文章的简钻
        Article article = articleMapper.selectById(articleId);
        BigDecimal diamondCount = article.getDiamondCount();
        diamondCount = diamondCount.add(new BigDecimal(diamond));
        article.setDiamondCount(diamondCount);
        article.setIsSettle(ArticleEnum.NOT_SETTLE.getCode());
        articleMapper.updateById(article);
        //减少当前登录用户的简钻余额
        user.setDiamondTotal(user.getDiamondTotal().subtract(new BigDecimal(diamond)));
        userMapper.updateById(user);
        //同步到用户动态表
        userActionService.newAction(userId,UserActionEnum.PRAISE.getCode(),articleId,UserActionEnum.ARTICLE.getCode());
    }

    /**
     * 取用户赞过的文章数量
     *
     * @param userId 用户id
     * @return Integer
     * @Author lghcode
     * @Date 2020/8/23 14:43
     */
    @Override
    public Integer getUserLikeArticleCount(Long userId) {
        return userArticleMapper.selectCount(new QueryWrapper<UserArticle>().lambda()
                .eq(UserArticle::getUserId,userId).eq(UserArticle::getType,UserEnum.USER_LIKE_ARTICLE.getCode()));
    }

    /**
     * 获取用户的文章列表数据
     *
     * @param userId 用户id
     * @return List<ArticleVo>
     * @Author lghcode
     * @Date 2020/8/23 14:59
     */
    @Override
    public List<ArticleVo> getUserArticles(Long userId) {
        return userArticleMapper.getUserArticles(userId);
    }
}
