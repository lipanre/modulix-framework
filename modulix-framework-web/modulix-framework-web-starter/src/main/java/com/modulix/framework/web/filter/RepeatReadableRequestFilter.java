package com.modulix.framework.web.filter;

import com.modulix.framework.web.api.http.RepeatReadableRequest;
import com.modulix.framework.web.service.HttpService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 可重复读请求拦截器
 *
 * <br>
 * {@code date} 2025/4/14 16:00
 */
@RequiredArgsConstructor
public class RepeatReadableRequestFilter extends OncePerRequestFilter {

    private final HttpService httpService;


    @SuppressWarnings("NullableProblems")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, IOException {
        if (httpService.isNeedCacheBody(request)) {
            filterChain.doFilter(new RepeatReadableRequest(request), response);
            return;
        }
        filterChain.doFilter(request, response);
    }
}