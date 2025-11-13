package com.modulix.framework.web.api.exception;

/**
 * 业务异常类
 *
 * @author lipanre
 */
public class BizException extends RuntimeException {

    public BizException(String template, Object... args) {

    }

    public BizException(String message) {
        super(message);
    }
}
