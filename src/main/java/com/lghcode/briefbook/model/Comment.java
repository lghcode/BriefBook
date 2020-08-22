package com.lghcode.briefbook.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.util.Date;

/**
 * 评论实体类
 * @Author lgh
 * @Date 2020/8/21 12:17
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long articleId;

    private Long pId;

    private Long fromUserId;

    private Long toUserId;

    private String cont;

    private Integer likeNum;

    private Date createTime;

}
