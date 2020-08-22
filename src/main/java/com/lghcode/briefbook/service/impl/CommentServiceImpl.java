package com.lghcode.briefbook.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lghcode.briefbook.enums.UserEnum;
import com.lghcode.briefbook.exception.BizException;
import com.lghcode.briefbook.mapper.CommentMapper;
import com.lghcode.briefbook.mapper.UserLikeCommentMapper;
import com.lghcode.briefbook.model.Comment;
import com.lghcode.briefbook.model.UserLikeComment;
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

    @Autowired
    private UserLikeCommentMapper userLikeCommentMapper;
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

    /**
     * 点赞或取消点赞 评论
     *
     * @param commentId 评论id
     * @param type      0-点赞，1-取消点赞
     * @param userId    当前登录用户id
     * @Author lghcode
     * @Date 2020/8/22 23:30
     */
    @Override
    public void likeComment(Long commentId, Integer type, Long userId) {
        //点赞
        if (type == UserEnum.FOLLOW.getCode()){
            Integer count = userLikeCommentMapper.selectCount(new QueryWrapper<UserLikeComment>().lambda()
                                            .eq(UserLikeComment::getUserId,userId)
                                            .eq(UserLikeComment::getCommentId,commentId)
            );
            if (count > 0){
                throw new BizException("对该评论已经点赞过");
            }
            UserLikeComment userLikeComment = UserLikeComment.builder()
                                        .userId(userId).commentId(commentId).build();
            userLikeCommentMapper.insert(userLikeComment);
        }else if(type == UserEnum.CANNEL_FOLLOW.getCode()){
            //取消点赞
            userLikeCommentMapper.delete(new QueryWrapper<UserLikeComment>().lambda()
                    .eq(UserLikeComment::getUserId,userId)
                    .eq(UserLikeComment::getCommentId,commentId));
        }
    }
}
