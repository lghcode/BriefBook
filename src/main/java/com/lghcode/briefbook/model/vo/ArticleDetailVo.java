package com.lghcode.briefbook.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 文章详情信息
 * @Author lgh
 * @Date 2020/8/19 21:50
 */
@Data
public class ArticleDetailVo {

    /**
     * 文章id
     */
    private Long articleId;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章内容
     */
    private String cont;

    /**
     * 简钻数量
     */
    private BigDecimal diamondCount;

    /**
     * 点赞量
     */
    private Long likeCount;

    /**
     * 是否点赞
     */
    private boolean isLike;

    /**
     * 0 - 打开评论 1-关闭评论
     */
    private Integer isOpenComment;

    /**
     * 阅读量
     */
    private Long readCount;

    /**
     * 文章发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date publishTime;

    /**
     * 文集名称
     */
    private String corpusName;

    /**
     * 作者用户id
     */
    private Long userId;
    /**
     * 作者用户头像
     */
    private String headImg;

    /**
     * 作者用户名
     */
    private String userName;

    /**
     * 是否关注
     */
    private boolean isFollow;

    /**
     * 是否收藏
     */
    private boolean isCollect;

    /**
     * 赞赏用户列表数据
     */
    private List<PraiseUserVo> praiseUserVoList;

    /**
     * 评论数量
     */
    private Integer commentCount;

    /**
     * 评论数据列表
     */
    private List<CommentVo> commentVoList;
}
