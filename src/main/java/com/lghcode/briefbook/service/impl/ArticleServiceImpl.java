package com.lghcode.briefbook.service.impl;

import com.lghcode.briefbook.enums.UserEnum;
import com.lghcode.briefbook.mapper.ArticleMapper;
import com.lghcode.briefbook.mapper.UserArticleMapper;
import com.lghcode.briefbook.model.Article;
import com.lghcode.briefbook.model.UserArticle;
import com.lghcode.briefbook.model.param.PublishArticleParam;
import com.lghcode.briefbook.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author lgh
 * @Date 2020/8/18 15:50
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserArticleMapper userArticleMapper;

    /**
     * 发布文章
     *
     * @param articleParam 发布文章用的参数
     * @Author lghcode
     * @Date 2020/8/18 15:51
     */
    @Override
    public void publishArticle(PublishArticleParam articleParam) {
        Article article = Article.builder()
                .title(articleParam.getTitle())
                .cont(articleParam.getCont())
                .corpusId(articleParam.getCorpusId())
                .wordCount(articleParam.getWordCount())
                .accessStatus(articleParam.getAccessStatus())
                .publishTime(new Date())
                .updateTime(new Date())
                .build();
        articleMapper.insert(article);
        UserArticle userArticle = UserArticle.builder()
                .userId(articleParam.getUserId())
                .articleId(article.getId())
                .type(UserEnum.USER_PUBLIASH_ARTICLE.getCode())
                .createTime(new Date())
                .build();
        userArticleMapper.insert(userArticle);
    }
}
