package com.modulix.framework.security.handler;

import com.modulix.framework.web.aip.http.Response;
import com.modulix.framework.web.aip.util.WebUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 认证授权过程中全局异常处理器
 *
 * <br>
 * {@code date} 2025/2/7 17:03
 */
@Slf4j
@SuppressWarnings("NullableProblems")
public class FilterExceptionHandler extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            WebUtil.response(response, Response.fail("系统异常,请联系管理员", e));
        }
    }
}