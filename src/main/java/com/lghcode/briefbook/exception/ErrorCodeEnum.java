package com.lghcode.briefbook.exception;

import lombok.Getter;

/** 错误码 A-BB-CCC
 *  A:错误级别，如1代表系统级错误，2代表服务级错误；
 *  B:模块名称，一般公司不会超过99个项目；
 *  C:具体错误编号，自增即可，一个项目999种错误应该够用；
 * @Author lgh
 * @Date 2020/8/13 20:04
 */
@Getter
public enum ErrorCodeEnum implements IErrorCode {

    /**
     * 公共错误码
     */
    SYSTEM_ERROR("B100001","系统错误"),
    BUSINESS_ERROR("B200001","业务错误"),
    DATA_FORMAT_ERROR("B200002","数据格式错误"),
    DATA_NULL("B200003","数据为空"),
    FILE_NOT_EXIST("B200004","文件不存在"),
    ;

    private String errorCode;
    private String msg;


    ErrorCodeEnum(String errorCode, String msg) {
        this.errorCode = errorCode;
        this.msg = msg;
    }
}
