package com.modulix.framework.web.aip.http;

import com.modulix.framework.web.aip.constant.ResponseCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * 通用响应类
 *
 * @author lipanre
 */
@Data
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Response<T> {

    /**
     * 响应码
     */
    private int code;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 响应描述
     */
    private String message;

    /**
     * 异常信息
     */
    private String exception;

    /**
     * 成功响应
     */
    public static <T> Response<T> success(T data) {
        return success(ResponseCode.SUCCESS, data, "success");
    }

    /**
     * 成功响应
     */
    public static <T> Response<T> success(T data, String message) {
        return success(ResponseCode.SUCCESS, data, message);
    }

    /**
     * 成功响应
     */
    public static <T> Response<T> success(int code, T data) {
        return success(code, data, "success");
    }

    /**
     * 成功响应
     * @param code 响应码
     * @param data 响应数据
     * @param message 响应描述
     * @return 响应对象
     */
    public static <T> Response<T> success(int code, T data, String message) {
        return new Response<>(code, data, message, null);
    }

    /**
     * 失败响应
     */
    public static <T> Response<T> fail(int code, String message) {
        return fail(code, message, null);
    }

    /**
     * 失败响应
     */
    public static <T> Response<T> fail(String message, Throwable exception) {
        return fail(ResponseCode.BIZ_EXCEPTION, message, exception);
    }

    /**
     * 失败响应
     * @param code 响应码
     * @param message 响应描述
     * @param exception 异常信息
     * @return 响应对象
     */
    public static <T> Response<T> fail(int code, String message, Throwable exception) {
        log.error("系统异常", exception);
        return new Response<>(code, null, message, ExceptionUtils.getStackTrace(exception));
    }


}
