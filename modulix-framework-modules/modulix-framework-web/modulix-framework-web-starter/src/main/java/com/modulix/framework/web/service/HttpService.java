package com.modulix.framework.web.service;

import jakarta.servlet.http.HttpServletRequest;

/**
 * <br>
 * {@code date} 2025/4/14 16:05
 */
public interface HttpService {

    /**
     * 判断指定的请求是否需要缓存请求体
     *
     * @param request http请求对象
     * @return true - 需要缓存 <br> false - 不需要缓存
     */
    boolean isNeedCacheBody(HttpServletRequest request);
}
