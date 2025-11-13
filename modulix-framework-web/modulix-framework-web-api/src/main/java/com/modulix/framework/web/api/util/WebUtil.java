package com.modulix.framework.web.api.util;


import com.modulix.framework.common.core.util.JsonUtil;
import com.modulix.framework.web.api.exception.BizException;
import com.modulix.framework.web.api.http.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * web工具类
 *
 * @author lipanre
 */
public class WebUtil {

    public static void response(HttpServletResponse response, Response<?> responseData) {
        response(HttpStatus.OK, response, responseData);
    }

        @SuppressWarnings("deprecation")
    @SneakyThrows
    public static void response(HttpStatus httpStatus, HttpServletResponse response, Response<?> responseData) {
        response.setStatus(httpStatus.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        if (StringUtils.isNotEmpty(responseData.getMessage())) {
            responseData.setMessage(responseData.getMessage());
        }
        response.getWriter().write(JsonUtil.toJson(responseData));
        response.getWriter().flush();
    }

    /**
     * 读取请求体并转换为对象
     *
     * @param request 请求对象
     * @param clazz   对象类型
     * @param <T>     对象类型
     * @return 对象
     */
    public static <T> T readBody(HttpServletRequest request, Class<T> clazz) {
        return JsonUtil.fromJson(readBody(request), clazz);
    }

    /**
     * 读取请求体
     *
     * @param request 请求对象
     * @return 请求体字符串
     */
    public static String readBody(HttpServletRequest request) {
        try {
            return request.getReader().lines().collect(Collectors.joining());
        } catch (IOException e) {
            throw new BizException("http.fail.body.read.error");
        }
    }

}
