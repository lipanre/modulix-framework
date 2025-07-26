package com.modulix.framework.security.handler;

import com.modulix.framework.web.aip.http.Response;
import com.modulix.framework.web.aip.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * 当认证完成之后，访问的资源没有权限时会进入这里
 *
 * @author lipanre
 */
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 403禁止访问
        WebUtil.response(HttpStatus.FORBIDDEN, response, Response.fail(accessDeniedException.getMessage(), accessDeniedException));
    }
}
