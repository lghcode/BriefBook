package com.lghcode.briefbook.model;

import lombok.*;

import java.util.Date;

/**
 * 用户动作表
 * @Author lgh
 * @Date 2020/8/23 12:23
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAction {

    private Long id;

    private Long userId;

    /**
     * 0-关注，1-发表，2-赞，3-评论，4-收藏，5-赞赏，6-注册 ,7-订阅
     */
    private Integer action;

    private Long objId;

    /**
     * 0-用户，1-文章，2-评论，3-文集
     */
    private Integer type;

    private Date createTime;
}
