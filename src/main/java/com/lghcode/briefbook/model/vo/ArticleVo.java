package com.lghcode.briefbook.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lgh
 * @Date 2020/8/23 11:32
 */
@Data
public class ArticleVo {

    private Long id;

    private String title;

    private String cont;

    private BigDecimal diamondCount;

    private Long readCount;

    private Integer commentCount;

    private Long likeCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date publishTime;

}
