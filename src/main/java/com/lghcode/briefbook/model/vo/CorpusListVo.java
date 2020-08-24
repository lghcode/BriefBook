package com.lghcode.briefbook.model.vo;

import lombok.Data;

/**
 * @Author lgh
 * @Date 2020/8/24 9:20
 */
@Data
public class CorpusListVo {

    /**
     * 文集id
     */
    private Long corpusId;

    /**
     * 文集名称
     */
    private String name;

    /**
     * 文集作者
     */
    private String creator;

    /**
     * 文集所属文章的数量
     */
    private Integer articleCount;

    /**
     * 文集被关注的数量
     */
    private Integer followCount;
}
