package com.lghcode.briefbook.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.util.Date;

/**
 * 用户文章实体类
 * @Author:LaLion
 * @Date:2020/8/1320:33
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserArticle {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 0 -- 用户发布文章 1 -- 用户点赞文章
     * 2 -- 用户赞赏文章 3 -- 用户收藏文章
     */
    private Integer type;
    /**
     * 创建时间
     */
    private Date createTime;
}
