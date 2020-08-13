package com.lghcode.briefbook.exception;

/**
 * 错误码接口
 * @Author lgh
 * @Date 2020/8/13 19:58
 */
public interface IErrorCode {

    /**
     * 获取错误码
     */
    String getErrorCode();

    /**
     * 获取错误码消息
     */
    String getMsg();
}
