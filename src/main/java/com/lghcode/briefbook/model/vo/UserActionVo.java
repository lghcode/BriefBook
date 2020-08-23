package com.lghcode.briefbook.model.vo;

import lombok.Data;

/**
 * @Author lgh
 * @Date 2020/8/23 14:00
 */
@Data
public class UserActionVo {

    private Long userId;

    private String userNickName;
    /**
     * 0-关注，1-发表，2-赞，3-评论，4-收藏，5-赞赏，6-注册 ,7-订阅
     */
    private Integer action;

    private Long objId;

    private String objName;

    /**
     * 评论留言者昵称
     */
    private String nickName;

    /**
     * 评论留言者
     */
    private Long fromUserId;

    /**
     * 被回复评论者昵称
     */
    private String toNickName;

    /**
     * 被回复评论者
     */
    private Long toUserId;

    /**
     * 评论所属文章id
     */
    private Long articleId;

    /**
     * 评论所属文章标题
     */
    private String articleTitle;

}
