package com.lghcode.briefbook.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

/**
 * 当前登录用户数据
 * @Author:LaLion
 * @Date:2020/8/13 16:59
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUser {
    /**
     * 当前登录用户id
     */
    private long id;

    /**
     * 用户手机号
     */
    private String mobile;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户头像
     */
    private String heading;
    /**
     * 性别
     * 0 - 保密  1 - 男  2 - 女
     */
    private Integer sex;
    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date birthday;
    /**
     * 个人主页
     */
    private String homePage;
    /**
     * 用户个人简介
     */
    private String profile;
    /**
     * 简钻总数量号
     */
    private String diamondTotal;
    /**
     * 0 - 非简书创作者 1 - 是简书创作者
     */
    private Integer isCreator;

    /**
     * 登录token
     */
    private String token;

}
