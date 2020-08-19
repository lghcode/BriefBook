package com.lghcode.briefbook.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lghcode.briefbook.enums.UserEnum;
import com.lghcode.briefbook.mapper.ArticleMapper;
import com.lghcode.briefbook.mapper.UserArticleMapper;
import com.lghcode.briefbook.model.Article;
import com.lghcode.briefbook.model.UserArticle;
import com.lghcode.briefbook.model.param.PublishArticleParam;
import com.lghcode.briefbook.model.param.RecommendArticleParam;
import com.lghcode.briefbook.service.ArticleService;
import com.lghcode.briefbook.util.PageResponse;
import org.apache.commons.lang3.StringUtils;
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

    /**
     * 查询文章列表，当搜索关键词为空时，则查询推荐文章
     *
     * @param articleParam 请求文章参数
     * @return PageResponse<Article>
     * @Author lghcode
     * @Date 2020/8/19 15:02
     */
    @Override
    public PageResponse<Article> queryArticle(RecommendArticleParam articleParam) {
        IPage<Article> articles;
        //当搜索关键词为空时，则查询推荐文章
        if (StringUtils.isEmpty(articleParam.getKeyWord())) {
            //现在直接查询全部，后面再考虑推荐文章设计
            articles =  articleMapper.selectListPage(new Page<>(articleParam.getPageNum(),articleParam.getPageSize()));
        }else{
            //当搜索关键词非空时，则查询符合关键词的文章
            articles =  articleMapper.selectListPageWithKey(new Page<>(articleParam.getPageNum(),articleParam.getPageSize()),articleParam.getKeyWord());
        }
        if (articles == null){
            return null;
        }
        return PageResponse.<Article>builder()
                .currentPage(articleParam.getPageNum())
                .pageSize(articleParam.getPageSize())
                .totalPage(articles.getTotal())
                .totalRecord(articles.getTotal())
                .rows(articles.getRecords())
                .build();
    }
}
