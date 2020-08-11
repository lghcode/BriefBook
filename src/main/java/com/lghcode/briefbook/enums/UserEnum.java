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
