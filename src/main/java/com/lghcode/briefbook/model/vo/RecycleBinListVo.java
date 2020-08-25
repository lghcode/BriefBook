package com.lghcode.briefbook.model.vo;

import lombok.Data;

/**
 * @Author lgh
 * @Date 2020/8/25 20:22
 */
@Data
public class RecycleBinListVo {

    private Long articleId;

    private String articleTitle;

    private Integer cacheDay;

}
