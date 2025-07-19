package com.modulix.framework.mybatis.plus.api.base;

import com.github.yulichang.base.JoinService;
import com.github.yulichang.extension.mapping.base.MPJDeepService;
import com.modulix.framework.common.core.function.Hook;

/**
 * 基础service
 *
 * @author lipanre
 */
public interface BaseService<T extends BaseDomain> extends MPJDeepService<T>, JoinService<T> {

    /**
     * 在事务提交之后执行指定的hook操作
     *
     * @param hook 指定的hook操作
     */
    void afterCommit(Hook hook);

}
