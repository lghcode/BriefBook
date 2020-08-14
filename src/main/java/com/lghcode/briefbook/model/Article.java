package com.lghcode.briefbook.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 文章实体类
 * @Author:LaLion
 * @Date:2020/8/1318:39
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String cont;

    /**
     * 文章所属的文集
     */
    private Long corpusId;

    /**
     * 文章字数
     */
    private Long wordCount;

    /**
     * 阅读量
     */
    private Long readCount;

    /**
     * 点赞量
     */
    private Long likeCount;

    /**
     * 简钻数量
     */
    private Long diamondCount;

    /**
     * 0 - 打开评论 1-关闭评论
     */
    private Long isOpenComment;

    /**
     * 0 - 公开 1 - 私密
     */
    private Long  accessStatus;

    /**
     * 文章发布时间
     */
    private Date publicTime;

    /**
     * 文章更新时间
     */
    private Date updateTime;
}
