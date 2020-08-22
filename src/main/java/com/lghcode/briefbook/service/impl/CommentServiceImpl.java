package com.lghcode.briefbook.service.impl;

import com.lghcode.briefbook.mapper.CommentMapper;
import com.lghcode.briefbook.model.Comment;
import com.lghcode.briefbook.model.param.CommentPublishParam;
import com.lghcode.briefbook.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author lgh
 * @Date 2020/8/21 12:22
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 发表或回复评论
     *
     * @param publishParam 发表评论参数
     * @param userId 当前登录用户id
     * @Author lghcode
     * @Date 2020/8/22 15:34
     */
    @Override
    public void publishComment(CommentPublishParam publishParam,Long userId) {
        Comment comment = Comment.builder()
                .articleId(publishParam.getArticleId())
                .pId(publishParam.getPId())
                .fromUserId(userId)
                .toUserId(publishParam.getToUserId())
                .cont(publishParam.getCont())
                .likeNum(0)
                .createTime(new Date())
                .build();
        commentMapper.insert(comment);
    }
}
