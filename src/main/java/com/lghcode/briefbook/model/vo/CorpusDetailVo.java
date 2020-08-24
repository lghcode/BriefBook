package com.lghcode.briefbook.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author lgh
 * @Date 2020/8/24 10:12
 */
@Data
public class CorpusDetailVo {

    private Long corpusId;

    private String corpusName;

    private Long creatorId;

    private String creatorName;

    private Integer creatorWordCount;

    private Integer creatorLikeCount;

    private Integer articleCount;

    private List<ArticleVo> articleVoList;

    private Integer followCount;

    private List<UserBaseVo> followUserList;

    private boolean isFollow;
}
