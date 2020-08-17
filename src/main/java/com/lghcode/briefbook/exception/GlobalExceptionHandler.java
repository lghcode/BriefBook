package com.lghcode.briefbook.exception;

import com.lghcode.briefbook.util.ResultErrorJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.stream.Collectors;

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
     * 拦截方法内参数校验
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResultErrorJson exception(ConstraintViolationException e){
        String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        ResultErrorJson errorJson = new ResultErrorJson();
        errorJson.setStatus(400);
        errorJson.setErrorCode(ErrorCodeEnum.DATA_FORMAT_ERROR.getErrorCode());
        errorJson.setMsg(message);
        log.error("catch a error:{}",e.getMessage());
        return errorJson;
    }

    /**
     * 拦截表单参数校验
     */
    @ExceptionHandler(BindException.class)
    public ResultErrorJson bindException(BindException e){
        ResultErrorJson errorJson = new ResultErrorJson();
        errorJson.setStatus(400);
        errorJson.setErrorCode(ErrorCodeEnum.DATA_FORMAT_ERROR.getErrorCode());
        BindingResult bindingResult = e.getBindingResult();
        errorJson.setMsg(Objects.requireNonNull(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage()));
        log.error("catch a error:{}",e.getMessage());
        return errorJson;
    }

    /**
     * 拦截JSON参数校验
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultErrorJson bindException(MethodArgumentNotValidException e){
        ResultErrorJson errorJson = new ResultErrorJson();
        errorJson.setStatus(401);
        errorJson.setErrorCode(ErrorCodeEnum.DATA_FORMAT_ERROR.getErrorCode());
        BindingResult bindingResult = e.getBindingResult();
        errorJson.setMsg(Objects.requireNonNull(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage()));
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
        e.printStackTrace();
        log.error("catch a error:{}",e.getMessage());
        return errorJson;
    }
}
