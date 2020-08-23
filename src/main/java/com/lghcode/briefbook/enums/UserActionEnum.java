package com.lghcode.briefbook.enums;

/**
 * @Author lgh
 * @Date 2020/8/23 12:30
 */
public enum UserActionEnum implements BaseEnum {

    /**
     * 动作枚举
     */
    FOLLOW(0,"关注"),
    PUBLISH(1,"发表"),
    LIKE(2,"赞"),
    COMMENT(3,"评论"),
    COLLECT(4,"收藏"),
    PRAISE(5,"赞赏"),
    REGISTER(6,"注册"),
    SUBSCRIPTION(7,"订阅"),

    /**
     * 对象类型枚举
     */
    USER(0,"用户"),
    ARTICLE(1,"文章"),
    COMMEN(2,"评论"),
    CORPUS(3,"文集"),
    ;
    private int code;
    private String desc;


    UserActionEnum(int code, String desc) {
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
