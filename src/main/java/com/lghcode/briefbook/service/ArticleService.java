package com.lghcode.briefbook.service;

import com.lghcode.briefbook.model.Article;
import com.lghcode.briefbook.model.param.PublishArticleParam;
import com.lghcode.briefbook.model.param.RecommendArticleParam;
import com.lghcode.briefbook.util.PageResponse;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author lgh
 * @Date 2020/8/18 15:49
 */
@Transactional(rollbackFor = Exception.class)
public interface ArticleService {

    /**
     * 发布文章
     *
     * @Author lghcode
     * @param  articleParam 发布文章用的参数
     * @Date 2020/8/18 15:51
     */
    void publishArticle(PublishArticleParam articleParam);

    /**
     * 查询文章列表，当搜索关键词为空时，则查询推荐文章
     *
     * @Author lghcode
     * @param  articleParam 请求文章参数
     * @return PageResponse<Article>
     * @Date 2020/8/19 15:02
     */
    PageResponse<Article> queryArticle(RecommendArticleParam articleParam);
}
