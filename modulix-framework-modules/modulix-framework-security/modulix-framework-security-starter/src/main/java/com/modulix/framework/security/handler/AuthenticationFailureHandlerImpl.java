package com.modulix.framework.security.handler;

import com.modulix.framework.web.aip.constant.ResponseCode;
import com.modulix.framework.web.aip.http.Response;
import com.modulix.framework.web.aip.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

/**
 * 全局认证失败处理器
 *
 * @author lipanre
 */
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 认证失败
        WebUtil.response(response, Response.fail(ResponseCode.LOGIN_AGAIN, exception.getMessage(), exception));
    }
}
