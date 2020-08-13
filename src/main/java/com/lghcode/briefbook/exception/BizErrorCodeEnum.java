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
     * 用户模块错误码
     */
    USER_NOT_EXIST("E201003", "用户不存在"),
    LOGIN_FAIL("E201004", "登录失败"),
    PASWORD_ERROR("E201005", "密码错误"),
    ;
    private String errorCode;
    private String msg;


    BizErrorCodeEnum(String errorCode, String msg) {
        this.errorCode = errorCode;
        this.msg = msg;
    }
}
