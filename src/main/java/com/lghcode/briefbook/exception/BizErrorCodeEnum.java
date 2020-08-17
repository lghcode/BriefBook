package com.lghcode.briefbook.exception;

import lombok.Getter;

/**
 * 错误码 A-BB-CCC
 *  *  A:错误级别，如1代表系统级错误，2代表服务级错误；
 *  *  B:模块名称，一般公司不会超过99个项目；
 *  *  C:具体错误编号，自增即可，一个项目999种错误应该够用；
 * @Author lgh
 * @Date 2020/8/13 20:29
 */
@Getter
public enum BizErrorCodeEnum implements IErrorCode {

    /**
     * 用户
     */
    USER_NOT_EXIST("B201001", "用户不存在"),
    LOGIN_FAIL("B201002", "登录失败"),
    PASWORD_ERROR("B201003", "密码错误"),
    MOBILE_ILLEGAL("B201004","用户手机号不合法"),
    USER_SEX_ILLEGAL("B2010005","用户性别参数不合法"),
    USER_BIRTHDAY_ILLEGAL("B2010006","用户生日参数不合法"),
    USER_NICKNAME_REPEAT("B2010007","用户昵称已被占用"),
    USER_MOBILE_REPEAT("B2010007","用户手机号已被占用"),
    /**
     * 短信验证码
     */
    SMSCODE_NOT_EXIST("B202001","短信验证码不存在"),
    SMSCODE_EXPIRED("B202002","短信验证码已过期"),
    SMSCODE_INCORRECT("B202003","短信验证码输入不正确"),
    SMSCODE_ILLEGAL("B202004","短信验证码输入不合法"),
    SMSCODE_ONE_MINUTE_REPEAT("B202005","请不要在一分钟之内重复发送验证码"),
    SMSCODE_SEND_FAIL("B202006","验证码发送失败"),
    ;
    private String errorCode;
    private String msg;


    BizErrorCodeEnum(String errorCode, String msg) {
        this.errorCode = errorCode;
        this.msg = msg;
    }
}
