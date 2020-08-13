package com.lghcode.briefbook.util;

/**
 * @Author lgh
 * @Date 2020/8/13 20:45
 */
public class ResultErrorJson extends ResultJson {

    private String errorCode;

    public ResultErrorJson() {

    }

    public ResultErrorJson(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
