package com.lghcode.briefbook.model.vo;

import lombok.Data;

/**
 * @Author lgh
 * @Date 2020/8/24 8:06
 */
@Data
public class UserListVo {

    private Long userId;

    private String headImg;

    private String nickName;

    private Integer wordCount;

    private Integer likeCount;

    private boolean isFollow;
}
