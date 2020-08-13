package com.lghcode.briefbook.exception;

import lombok.Getter;

/**
 * 业务异常
 * @Author lgh
 * @Date 2020/8/13 20:18
 */
@Getter
public class BizException extends RuntimeException {

    private static final long serialVersionUID = -4738289739213229308L;

    private String errorCode = ErrorCodeEnum.BUSINESS_ERROR.getErrorCode();
    private String msg;

    public BizException(String msg) {
        super(ErrorCodeEnum.BUSINESS_ERROR.getErrorCode()+":"+msg);
        this.msg = msg;
    }

    public BizException(IErrorCode iErrorCode) {
        super(iErrorCode.getErrorCode()+":"+ iErrorCode.getMsg());
        this.errorCode = iErrorCode.getErrorCode();
        this.msg = iErrorCode.getMsg();
    }

    public BizException(IErrorCode iErrorCode, Throwable throwable) {
        super(iErrorCode.getErrorCode()+":"+ iErrorCode.getMsg(),throwable);
        this.errorCode = iErrorCode.getErrorCode();
        this.msg = iErrorCode.getMsg();
    }

}
