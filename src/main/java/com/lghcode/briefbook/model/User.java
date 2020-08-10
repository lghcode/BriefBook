package com.lghcode.briefbook.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户类
 * @Author lgh
 * @Date 2020/8/10 10:02
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像url
     */
    private String headImg;

    /**
     * 性别 ,0-保密，1-男，2-女 ( 默认为0)
     */
    private Integer sex;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 个人主页地址
     */
    private String homePage;

    /**
     * 个人简介
     */
    private String profile;

    /**
     * 用户的简钻数量总和
     */
    private BigDecimal diamondTotal;

    /**
     * 是否为简书创造者，0--非简书创作者，1--是简书创作者
     */
    private Integer isCreator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
