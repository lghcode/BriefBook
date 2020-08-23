package com.lghcode.briefbook.model;

import lombok.*;

/**
 * 用户-文集 类
 * @Author lgh
 * @Date 2020/8/23 14:32
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCorpus {

    private Long id;

    private Long userId;

    private Long corpusId;

    private Long createTime;
}
