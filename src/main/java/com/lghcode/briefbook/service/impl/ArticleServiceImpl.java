package com.lghcode.briefbook.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lghcode.briefbook.constant.Constant;
import com.lghcode.briefbook.enums.UserActionEnum;
import com.lghcode.briefbook.enums.UserEnum;
import com.lghcode.briefbook.exception.BizException;
import com.lghcode.briefbook.mapper.*;
import com.lghcode.briefbook.model.*;
import com.lghcode.briefbook.model.param.PublishArticleParam;
import com.lghcode.briefbook.model.param.RecommendArticleParam;
import com.lghcode.briefbook.model.vo.*;
import com.lghcode.briefbook.service.ArticleService;
import com.lghcode.briefbook.service.UserActionService;
import com.lghcode.briefbook.util.JwtTokenUtil;
import com.lghcode.briefbook.util.PageResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    @Autowired
    private UserActionService userActionService;

    @Autowired
    private CorpusMapper corpusMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserFansMapper userFansMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserLikeCommentMapper userLikeCommentMapper;

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
        //同步到用户动态表
        userActionService.newAction(articleParam.getUserId(),UserActionEnum.PUBLISH.getCode(),article.getId(),UserActionEnum.ARTICLE.getCode());
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

    /**
     * 根据文章id获取文章相关信息
     *
     * @param authToken 用户token
     * @param articleId 文章id不能为空
     * @return ArticleDetailVo
     * @Author lghcode
     * @Date 2020/8/19 21:37
     */
    @Override
    public ArticleDetailVo getArticleById(String authToken, Long articleId) {
        Article article = articleMapper.selectById(articleId);
        if (article == null) {
            throw new BizException("该文章不存在");
        }
        ArticleDetailVo articleDetailVo = new ArticleDetailVo();
        BeanUtils.copyProperties(article,articleDetailVo);
        articleDetailVo.setArticleId(article.getId());
        //增加该文章的阅读量
        article.setReadCount(article.getReadCount()+1);
        articleMapper.updateById(article);
        //得到文集名称
        Corpus corpus = corpusMapper.selectById(article.getCorpusId());
        articleDetailVo.setCorpusName(corpus.getName());
        //根据文章id，查询该文章的发布作者
        UserBaseVo userBaseVo = userArticleMapper.getUserByArticleId(articleId);
        if (userBaseVo == null) {
            throw new BizException("业务数据异常，文章的发布作者数据丢失");
        }
        articleDetailVo.setUserId(userBaseVo.getUserId());
        articleDetailVo.setUserName(userBaseVo.getUserName());
        articleDetailVo.setHeadImg(userBaseVo.getHeadImg());
        //获取文章的赞赏用户列表数据
        List<PraiseUserVo> praiseUserVos = userArticleMapper.getPraiseUserListByArticleId(articleId);
        articleDetailVo.setPraiseUserVoList(praiseUserVos);
        //获取文章的评论数量
        Integer commentCount = commentMapper.selectCount(new QueryWrapper<Comment>().lambda()
                .eq(Comment::getArticleId,articleId));
        articleDetailVo.setCommentCount(commentCount);
        //评论数据列表
        List<CommentVo> commentVos = commentMapper.getParentComments(articleId);
        if (commentVos.size() != 0){
            for (CommentVo commentVo : commentVos){
                List<ChildrenCommentVo> childrenCommentVos = commentMapper.getChildrenComments(commentVo.getCommentId());
                commentVo.setChildrens(childrenCommentVos);
            }
        }
        //如果authToken不等于空并且没有过期
        if (!StringUtils.isBlank(authToken) && redisTemplate.hasKey(Constant.REDIS_LOGIN_KEY+authToken)){
            //判断当前登录用户有没有给该文章点赞
            Long curLoginUserId = jwtTokenUtil.getUserIdFromToken(authToken);
            Integer res = userArticleMapper.selectCount(new QueryWrapper<UserArticle>().lambda()
                    .eq(UserArticle::getUserId,curLoginUserId)
                    .eq(UserArticle::getArticleId,articleId)
                    .eq(UserArticle::getType,UserEnum.USER_LIKE_ARTICLE.getCode()));
            if (res > 0){
                articleDetailVo.setLike(true);
            }
            //判断当前登录用户有没有给该文章收藏
            Integer res2 = userArticleMapper.selectCount(new QueryWrapper<UserArticle>().lambda()
                    .eq(UserArticle::getUserId,curLoginUserId)
                    .eq(UserArticle::getArticleId,articleId)
                    .eq(UserArticle::getType,UserEnum.USER_COLLECT_ARTICLE.getCode()));
            if (res2 > 0){
                articleDetailVo.setCollect(true);
            }
            //判断当前登录用户有没有对该文章作者关注
            Integer m = userFansMapper.selectCount(new QueryWrapper<UserFans>().lambda()
                                        .eq(UserFans::getUserId,userBaseVo.getUserId())
                                        .eq(UserFans::getFansId,curLoginUserId));
            if (m > 0){
                articleDetailVo.setFollow(true);
            }
            //判断当前登录用户有没有对评论点过赞
            for (CommentVo commentVo : commentVos){
                Integer count = userLikeCommentMapper.selectCount(new QueryWrapper<UserLikeComment>().lambda()
                                        .eq(UserLikeComment::getUserId,curLoginUserId)
                                        .eq(UserLikeComment::getCommentId,commentVo.getCommentId())
                );
                if (count > 0){
                    commentVo.setLike(true);
                }
            }
        }
        articleDetailVo.setCommentVoList(commentVos);
        return articleDetailVo;
    }
}
