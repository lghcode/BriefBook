package com.lghcode.briefbook.model.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author lgh
 * @Date 2020/8/19 14:42
 */
@Data
public class RecommendArticleParam {

    /**
     * 搜索关键词
     */
    private String keyWord;

    /**
     * 页码
     */
    @NotNull(message = "页码不能为空")
    private Integer pageNum;

    /**
     * 每页请求的数量
     */
    @NotNull(message = "每页请求的数量不能为空")
    private Integer pageSize;
}
