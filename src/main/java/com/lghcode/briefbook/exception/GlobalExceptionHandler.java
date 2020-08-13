package com.lghcode.briefbook.exception;

import com.lghcode.briefbook.util.ResultErrorJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 * @Author lgh
 * @Date 2020/8/13 20:35
 */
@RestControllerAdvice
@Order(-1)
@Slf4j
public class GlobalExceptionHandler {


    /**
     * 拦截业务异常
     * @return ResultErrorJson
     */
    @ExceptionHandler(BizException.class)
    public ResultErrorJson bizException(BizException e){
        ResultErrorJson errorJson = new ResultErrorJson();
        errorJson.setStatus(500);
        errorJson.setErrorCode(e.getErrorCode());
        errorJson.setMsg(e.getMsg());
        log.error("catch a error:{}",e.getMessage());
        return errorJson;
    }


    /**
     * 拦截系统异常
     * @return ResultErrorJson
     */
    @ExceptionHandler(Exception.class)
    public ResultErrorJson exception(Exception e){
        ResultErrorJson errorJson = new ResultErrorJson();
        errorJson.setStatus(500);
        errorJson.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getErrorCode());
        errorJson.setMsg(ErrorCodeEnum.SYSTEM_ERROR.getMsg());
        log.error("catch a error:{}",e.getMessage());
        return errorJson;
    }
}
