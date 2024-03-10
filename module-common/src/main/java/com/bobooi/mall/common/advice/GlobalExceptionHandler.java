package com.bobooi.mall.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import com.bobooi.mall.common.exception.ApplicationException;
import com.bobooi.mall.common.response.ApplicationResponse;

import javax.servlet.http.HttpServletRequest;

import static com.bobooi.mall.common.response.SystemCodeEnum.*;

/**
 * @author bobo
 * @date 2021/3/31
 */

@RestController
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ApplicationResponse<String> defaultErrorHandler(HttpServletRequest request, Exception exception){
        if(exception instanceof ApplicationException){
            ApplicationException applicationException = (ApplicationException) exception;
            log.info("捕获应用错误："+ applicationException.getResponseMessage());
            return ApplicationResponse.fail(applicationException.getSystemCode(), applicationException.getResponseMessage());
        } else if (exception instanceof IllegalArgumentException
                || exception instanceof HttpMessageNotReadableException
                || exception instanceof BindException) {
            log.info("捕获参数错误");
            return ApplicationResponse.fail(ARGUMENT_WRONG);
        } else if (exception instanceof HttpRequestMethodNotSupportedException) {
            log.debug("捕获调用方法错误");
            return ApplicationResponse.fail(REQUEST_METHOD_NOT_SUPPPORTED);
        } else if (exception instanceof ShiroException){
            log.debug("捕获权限错误");
            return ApplicationResponse.fail(NO_PERMISSION);
        }
        log.error("捕获意外异常", exception);
        return ApplicationResponse.fail(UNKNOWN_ERROR);
    }



}
