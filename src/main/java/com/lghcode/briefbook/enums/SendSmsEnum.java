package com.lghcode.briefbook.enums;

/**
 * @Author lgh
 * @Date 2020/8/12 12:32
 */
public enum SendSmsEnum implements BaseEnum{

    /**
     * 验证码发送场景类型枚举
     */
    LOGIN_SMS(0,"登录验证码"),
    RESET_SMS(1,"重置密码验证码"),
    UPMOBILE_SMS(2,"更换手机号验证码")
    ;


    private int code;
    private String desc;


    SendSmsEnum(int code, String desc) {
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
