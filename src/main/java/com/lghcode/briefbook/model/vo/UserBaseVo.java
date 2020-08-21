package com.lghcode.briefbook.model.vo;

import lombok.Data;

/**
 * @Author lgh
 * @Date 2020/8/20 21:53
 */
@Data
public class UserBaseVo {

    /**
     * 作者用户id
     */
    private Long userId;
    /**
     * 作者用户头像
     */
    private String headImg;

    /**
     * 作者用户名
     */
    private String userName;

}
