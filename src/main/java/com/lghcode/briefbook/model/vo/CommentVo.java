package com.lghcode.briefbook.model.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 评论Vo类
 * @Author lgh
 * @Date 2020/8/19 22:36
 */
@Data
public class CommentVo {

    private Long commentId;

    private boolean isLike;

    private Long userId;

    private String userName;

    private String headImg;

    private String comment;

    private Date createTime;

    private Integer likeNum;

    List<ChildrenCommentVo> childrens;
}
