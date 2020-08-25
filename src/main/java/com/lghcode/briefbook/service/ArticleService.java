package com.lghcode.briefbook.service;

import com.lghcode.briefbook.model.Article;
import com.lghcode.briefbook.model.param.PublishArticleParam;
import com.lghcode.briefbook.model.param.RecommendArticleParam;
import com.lghcode.briefbook.model.param.UpdateArticleParam;
import com.lghcode.briefbook.model.vo.ArticleDetailVo;
import com.lghcode.briefbook.model.vo.ArticleVo;
import com.lghcode.briefbook.model.vo.RecycleBinListVo;
import com.lghcode.briefbook.util.PageResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    /**
     * 根据文章id获取文章相关信息
     *
     * @Author lghcode
     * @param  authToken 用户token
     * @param  articleId 文章id不能为空
     * @return ResultJson
     * @Date 2020/8/19 21:37
     */
    ArticleDetailVo getArticleById(String authToken, Long articleId);

    /**
     * 查看用户赞过的文章列表
     *
     * @Author lghcode
     * @param userId 用户id
     * @return List<ArticleVo>
     * @Date 2020/8/25 9:59
     */
    List<ArticleVo> queryUserLikeArticles(Long userId);

    /**
     * 查看用户收藏过的文章列表
     *
     * @Author lghcode
     * @param userId 用户id
     * @return  List<ArticleVo>
     * @Date 2020/8/25 9:59
     */
    List<ArticleVo> queryUserCollectArticles(Long userId);

    /**
     * 查看用户的公开文章列表
     *
     * @Author lghcode
     * @param  userId 用户id
     * @return  List<ArticleVo>
     * @Date 2020/8/25 14:55
     */
    List<ArticleVo> queryUserPublicArticles(Long userId);

    /**
     * 查看用户的私密文章列表
     *
     * @Author lghcode
     * @param  userId 用户id
     * @return  List<ArticleVo>
     * @Date 2020/8/25 14:55
     */
    List<ArticleVo> queryUserPrivateArticles(Long userId);

    /**
     * 更新文章
     *
     * @Author lghcode
     * @param updateArticleParam 更新参数
     * @Date 2020/8/25 15:23
     */
    void updateArticle(UpdateArticleParam updateArticleParam);

    /**
     * 将文章设置为公开或私密
     *
     * @Author lghcode
     * @param articleId 文章id
     * @param code 0-公开  1-私密
     * @Date 2020/8/25 15:23
     */
    void setArticleAccess(Long articleId, int code);

    /**
     * 删除文章
     *
     * @Author lghcode
     * @param articleId 文章id
     * @Date 2020/8/25 18:26
     */
    void deleteArticle(Long articleId);

    /**
     * 根据文章id获取文章内容
     *
     * @Author lghcode
     * @param  articleId 文章id不能为空
     * @return Article
     * @Date 2020/8/25 18:52
     */
    Article getOneById(Long articleId);

    /**
     * 获取用户的回收站文章列表
     *
     * @Author lghcode
     * @param curUserId 当前登录用户id
     * @return ResultJson
     * @Date 2020/8/25 19:08
     */
    List<RecycleBinListVo> getRecycleBinList(Long curUserId);

    /**
     * 彻底删除文章
     *
     * @Author lghcode
     * @param articleId 文章id
     * @Date 2020/8/25 18:26
     */
    void deleteForeverArticle(Long articleId);

    /**
     * 恢复文章
     *
     * @Author lghcode
     * @param articleId 文章id
     * @Date 2020/8/25 18:26
     */
    void restoreArticle(Long articleId);

    /**
     * 每天对回收站的文章的剩余天数减少1天，如果剩余天数为零，则彻底删除该文章
     * 每天凌晨0点1分执行
     */
    void runRecycleArticleTask();
}
