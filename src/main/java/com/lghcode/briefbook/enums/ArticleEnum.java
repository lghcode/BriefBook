package com.lghcode.briefbook.enums;

/**
 * @Author lgh
 * @Date 2020/8/23 20:58
 */
public enum  ArticleEnum implements BaseEnum{

    /**
     *清算状态
     */
    NOT_SETTLE(0,"未清算"),
    ALREADY_SETTLE(1,"已清算"),

    /**
     * 文章访问枚举
     */
    PUBLIC_ARTICLE(0,"公开"),
    PRIVATE_ARTICLE(1,"私密"),

    /**
     * 文章状态
     */
    NORMAL(0,"正常"),
    RECYCLE(1,"回收"),
    DELETE(2,"删除")
    ;
    private int code;
    private String desc;

    ArticleEnum(int code, String desc) {
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
