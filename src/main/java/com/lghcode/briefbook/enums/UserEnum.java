package com.lghcode.briefbook.enums;

/**
 * @Author lgh
 * @Date 2020/8/11 17:28
 */
public enum  UserEnum implements BaseEnum{

    /**
     * 性别枚举
     */
    SEX_SECRET(0,"保密"),
    SEX_MALE(1,"男"),
    SEX_FEMALE(2,"女"),


    /**
     * 用户-文章关系枚举
     */
    USER_PUBLIASH_ARTICLE(0,"用户发布文章"),
    USER_LIKE_ARTICLE(1,"用户点赞文章"),
    USER_PRAISE_ARTICLE(2,"用户赞赏文章"),
    USER_COLLECT_ARTICLE(3,"用户收藏文章"),

    /**
     * 关注或者取消关注
     */
    FOLLOW(0,"关注"),
    CANNEL_FOLLOW(1,"取消关注")
    ;


    private int code;
    private String desc;


    UserEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 返回枚举码
     *
     * @return int
     * @Author lghcode
     * @Date 2020/8/11 15:06
     */
    @Override
    public int getCode() {
        return code;
    }

    /**
     * 返回枚举描述
     *
     * @return String
     * @Author lghcode
     * @Date 2020/8/11 15:06
     */
    @Override
    public String getDesc() {
        return desc;
    }
}
