package com.modulix.framework.security.api;

/**
 * 租户service
 *
 * @author lipanre
 */
public interface TenantInfoService {

    /**
     * 获取租户id
     *
     * @return 租户id
     */
    Long getTenantId(String serverName);

}
