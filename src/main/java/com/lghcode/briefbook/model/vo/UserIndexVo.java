package com.lghcode.briefbook.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author lgh
 * @Date 2020/8/23 11:19
 */
@Data
public class UserIndexVo {

    private Long id;

    private boolean isFollow;

    private String headImg;

    private String nickName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date birthday;

    private String profile;

    private BigDecimal diamondTotal;

    /**
     * 关注数量
     */
    private Integer attentionCount;
    /**
     * 粉丝数量
     */
    private Integer fansCount;

    /**
     * 文章总字数
     */
    private Integer wordCount;
    /**
     * 收获文章赞的数量
     */
    private Integer approvalCount;

    /**
     * 用户文章数量
     */
    private Integer articleCount;

    /**
     * 用户文集数量
     */
    private Integer corpusCount;

    /**
     * 用户赞过的文章数量
     */
    private Integer likeArticleCount;

    /**
     * 用户关注的文集数量
     */
    private Integer followCorpusCount;

    /**
     * 用户文章列表
     */
    private List<ArticleVo> articleVos;

    /**
     * 用户动态列表
     */
    public List<UserActionVo> userActionVos;

}
