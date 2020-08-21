package com.lghcode.briefbook.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lghcode.briefbook.model.Comment;
import com.lghcode.briefbook.model.vo.ChildrenCommentVo;
import com.lghcode.briefbook.model.vo.CommentVo;

import java.util.List;

/**
 * @Author lgh
 * @Date 2020/8/21 12:21
 */
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 获取父评论集合根据文章id
     *
     * @Author lghcode
     * @param  articleId 文章id
     * @return List<CommentVo>
     * @Date 2020/8/21 14:11
     */
    List<CommentVo> getParentComments(Long articleId);

    /**
     * 获取子评论集合根据文章父id
     *
     * @Author lghcode
     * @param parentId 文章父id
     * @return List<ChildrenCommentVo>
     * @Date 2020/8/21 14:27
     */
    List<ChildrenCommentVo> getChildrenComments(Long parentId);
}
