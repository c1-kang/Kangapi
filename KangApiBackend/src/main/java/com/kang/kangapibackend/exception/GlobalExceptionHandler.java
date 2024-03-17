package com.kang.kangapibackend.exception;

import com.kang.kangapibackend.common.BaseResponse;
import com.kang.kangapibackend.common.ErrorCode;
import com.kang.kangapibackend.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * BusinessException 异常处理
     * @param e e
     * @return BaseResponse<T>
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> BusinessHandler(BusinessException e) {
        log.error("BusinessException: " + e);
        return ResultUtils.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> RuntimeException(RuntimeException e) {
        log.error("RuntimeException: " + e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR);
    }
}
