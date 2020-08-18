package com.lghcode.briefbook.service;

import com.lghcode.briefbook.model.param.PublishArticleParam;
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
}
