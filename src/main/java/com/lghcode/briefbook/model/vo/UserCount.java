package com.lghcode.briefbook.model.vo;

import lombok.*;

/**
 * 用户相关数量统计
 * @Author:LaLion
 * @Date:2020/8/1317:21
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCount {
    /**
     * 关注数量
     */
    private Integer attentionCount;
    /**
     * 粉丝数量
     */
    private Integer fansCount;
    /**
     * 私密文章数量
     */
    private Integer privateArticleCount;
    /**
     * 文章总字数
     */
    private Integer wordCount;
    /**
     * 收获文章赞的数量
     */
    private Integer approvalCount;
}
