package com.modulix.framework.security.api;

/**
 *
 *
 * @author lipanre
 */
public interface SecurityServiceFactory {

    /**
     * 获取默认的 SecurityService 实例
     *
     * @param clientType 客户端类型
     * @return 安全服务实例
     */
    default SecurityService getDefaultSecurityService(String clientType) {
        return getSecurityService(clientType + SecurityService.BEAN_NAME_SUFFIX);
    }

    /**
     * 获取指定名称的 SecurityService 实例
     *
     * @param name 安全服务名称
     * @return 安全服务实例
     */
    SecurityService getSecurityService(String name);

}
