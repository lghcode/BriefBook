package com.lghcode.briefbook.model.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author lgh
 * @Date 2020/8/22 15:09
 */
@Data
public class CommentPublishParam {

    @NotNull(message = "文章id不能为空")
    private Long articleId;

    @NotNull(message = "评论父id不能为空")
    private Long pId;

    @NotNull(message = "被回复人的id")
    private Long toUserId;

    @NotEmpty(message = "评论内容不能为空")
    private String cont;
}
