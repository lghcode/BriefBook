package com.lghcode.briefbook.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.util.Date;

/**
 * 用户点赞评论表
 * @Author lgh
 * @Date 2020/8/22 23:10
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLikeComment {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long commentId;

    private Date likeTime;
}
