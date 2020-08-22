package com.lghcode.briefbook.service;

import com.lghcode.briefbook.model.param.CommentPublishParam;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author lgh
 * @Date 2020/8/21 12:22
 */
@Transactional(rollbackFor = Exception.class)
public interface CommentService {


    /**
     * 发表或回复评论
     *
     * @Author lghcode
     * @param  publishParam 发表评论参数
     * @param  userId 当前登录用户id
     * @Date 2020/8/22 15:34
     */
    void publishComment(CommentPublishParam publishParam,Long userId);

    /**
     * 点赞或取消点赞 评论
     *
     * @Author lghcode
     * @param commentId 评论id
     * @param type 0-点赞，1-取消点赞
     * @param userId 当前登录用户id
     * @Date 2020/8/22 23:30
     */
    void likeComment(Long commentId,Integer type,Long userId);
}
