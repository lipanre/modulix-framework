package com.modulix.framework.web.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.modulix.framework.web.aip.constant.ResponseCode;
import com.modulix.framework.web.aip.exception.BizException;
import com.modulix.framework.web.aip.http.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidKeySpecException.class)
    public Response<String> invalidKeySpecException(InvalidKeySpecException e) {
        return Response.fail(ResponseCode.LOGIN_AGAIN, "token解析异常,请重新登录");
    }

    /**
     * 业务异常
     *
     * @param e 业务异常对象
     * @return 错误对象
     */
    @ExceptionHandler(BizException.class)
    public Response<String> bizException(BizException e) {
        return Response.fail(e.getMessage(), e);
    }

    /**
     * 请求参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<String> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        return Response.fail(e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), e);
    }

    /**
     * http请求method不对
     *
     * @param e 异常对象
     * @return 响应
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response<String> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        if (Objects.nonNull(e.getSupportedMethods())) {
            String message = String.format("请求方式不支持，当前: %s, 期望: %s", e.getMethod(), String.join(",", e.getSupportedMethods()));
            return Response.fail(message, e);
        }
        return Response.fail("请求方式不支持", e);
    }

    /**
     * 方法参数校验异常
     *
     * @param e 异常对象
     * @return 异常响应
     */
    @ExceptionHandler(HandlerMethodValidationException.class)
    public Response<String> handlerMethodValidationException(HandlerMethodValidationException e) {
        return Response.fail(e.getValueResults().get(0).getResolvableErrors().get(0).getDefaultMessage(), e);
    }

    /**
     * Http消息不可读异常。
     * <p>
     * 报错原因包括（不完全的列举）：
     * <p>
     * （1）缺少请求体（RequestBody）异常;
     * <p>
     * （2）无效格式异常。比如：参数为数字，但是前端传递的是字符串且无法解析成数字。
     * <p>
     * （3）Json解析异常（非法Json格式）。传递的数据不是合法的Json格式。比如：key-value对中的value(值)为String类型，却没有用双引号括起来。
     * <p>
     * 举例：
     * （1）缺少请求体（RequestBody）异常。报错：
     * DefaultHandlerExceptionResolver : Resolved [org.springframework.client.converter.HttpMessageNotReadableException:
     * Required request body is missing:     *   public void com.example.web.user.controller.UserController.addUser(com.example.web.model.param.UserAddParam)]     *     * @param e 异常对象
     *
     * @return 异常响应对象
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Response<String> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        Throwable rootCause = e.getRootCause();

        // 无效格式异常处理。比如：目标格式为数值，输入为非数字的字符串（"80.5%"、"8.5.1"、"张三"）。  
        if (rootCause instanceof InvalidFormatException cause) {
            // 目标类型  
            Class<?> targetType = cause.getTargetType();
            // 目标类型提示信息  
            String targetTypeNotification = "";
            if (targetType == BigInteger.class || targetType == Integer.class || targetType == Long.class
                    || targetType == Short.class || targetType == Byte.class) {
                targetTypeNotification = "参数类型应为：整数";
            } else if (targetType == BigDecimal.class || targetType == Double.class || targetType == Float.class) {
                targetTypeNotification = "参数类型应为：数值";
            }
            Object value = cause.getValue();
            return Response.fail(HttpStatus.BAD_REQUEST.value(), String.format("参数格式错误！%s当前输入参数：[%s]",
                    targetTypeNotification, value), e);
        }
        String userMessage = "Http消息不可读异常！请稍后重试，或联系管理员处理";
        return Response.fail(HttpStatus.BAD_REQUEST.value(), userMessage, e);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public Response<String> noResourceFoundException(NoResourceFoundException e) {
        return Response.fail(HttpStatus.NOT_FOUND.value(), e.getMessage(), e);
    }


    /**
     * 未知异常
     *
     * @return 错误对象
     */
    @ExceptionHandler(Exception.class)
    public Response<String> exception(Exception e) {
        log.error("系统发生异常: ", e);
        return Response.fail("系统异常, 请联系管理员", e);
    }


}