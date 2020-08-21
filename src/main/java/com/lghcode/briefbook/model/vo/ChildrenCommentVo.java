package com.lghcode.briefbook.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * 子评论Vo类
 * @Author lgh
 * @Date 2020/8/19 22:36
 */
@Data
public class ChildrenCommentVo {

    private Long commentId;

    private Long userId;

    private String userName;

    private Long toUserId;

    private String toUserName;

    private String headImg;

    private String comment;

    private Date createTime;

}
