package com.kang.kangapibackend.common;

/**
 * 返回工具类
 */
public class ResultUtils {
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "");
    }

    public static <T> BaseResponse<T> error(ErrorCode errorCode, T data) {
        return new BaseResponse<>(errorCode, data);
    }

    public static <T> BaseResponse<T> error(ErrorCode errorCode, T data, String message) {
        return new BaseResponse<>(errorCode, data, message);
    }

    public static BaseResponse<?> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    public static BaseResponse<?> error(ErrorCode errorCode, String message) {
        return new BaseResponse<>(errorCode, message);
    }

    public static BaseResponse<?> error(int code, String message) {
        return new BaseResponse<>(code, message);
    }

}
