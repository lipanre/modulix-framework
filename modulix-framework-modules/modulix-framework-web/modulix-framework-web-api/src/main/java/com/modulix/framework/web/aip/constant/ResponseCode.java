package com.modulix.framework.web.aip.constant;

/**
 * 业务响应码
 *
 * @author lipanre
 */
public interface ResponseCode {

    /**
     * 成功响应
     */
    int SUCCESS = 2000;

    /**
     * 刷新token
     */
    int REFRESH_TOKEN = 4001;

    /**
     * 重新登录
     */
    int LOGIN_AGAIN = 4002;

    /**
     * 业务异常
     */
    int BIZ_EXCEPTION = 5000;

}
