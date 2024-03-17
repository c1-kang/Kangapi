package com.kang.kangapibackend.common;

import lombok.Data;

/**
 * 通用返回类
 */
@Data
public class BaseResponse<T> {
    private final int code;

    private final String message;

    private final T data;

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public BaseResponse(ErrorCode errorCode, T data) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.data = data;
    }

    public BaseResponse(ErrorCode errorCode, T data, String message) {
        this.code = errorCode.getCode();
        this.message = message;
        this.data = data;
    }

    public BaseResponse(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.data = null;
    }

    public BaseResponse(ErrorCode errorCode, String message) {
        this.code = errorCode.getCode();
        this.message = message;
        this.data = null;
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.data = null;
        this.message = message;
    }

}
