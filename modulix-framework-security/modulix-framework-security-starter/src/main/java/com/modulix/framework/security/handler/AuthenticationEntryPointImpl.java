package com.modulix.framework.security.handler;

import com.modulix.framework.web.aip.constant.ResponseCode;
import com.modulix.framework.web.aip.http.Response;
import com.modulix.framework.web.aip.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 *  当访问受保护的资源时未认证，会进入这里
 *
 * @author lipanre
 */
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        WebUtil.response(response, Response.fail(ResponseCode.REFRESH_TOKEN, authException.getMessage(), authException));
    }
}
