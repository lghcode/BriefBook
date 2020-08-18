package com.lghcode.briefbook.model.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 发布文章请求参数
 * @Author lgh
 * @Date 2020/8/18 15:32
 */
@Data
public class PublishArticleParam {

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空")
    private Long userId;

    /**
     *文章标题
     */
    @NotEmpty(message = "文章标题不能为空")
    private String title;


    /**
     *文章内容
     */
    @NotEmpty(message = "文章内容不能为空")
    private String cont;

    /**
     * 文章字数
     */
    @NotNull(message = "文章字数不能为空")
    private Long wordCount;

    /**
     * 文集id
     */
    @NotNull(message = "文集id不能为空")
    private Long corpusId;

    /**
     * 0-公开，1-私密
     */
    @NotNull(message = "文章类型不能为空")
    private Integer accessStatus;
}
