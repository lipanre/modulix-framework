package com.modulix.framework.mybatis.plus.api.base;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.modulix.framework.common.core.function.Hook;
import io.github.linpeilie.Converter;
import jakarta.annotation.Resource;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 基础serviceImpl
 *
 * @author lipanre
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseDomain> extends MPJBaseServiceImpl<M, T> implements BaseService<T> {

    @Resource
    protected Converter converter;

    /**
     * 在事务提交之后执行指定的hook操作
     *
     * @param hook 指定的hook操作
     */
    public void afterCommit(Hook hook) {
        // 事务提交后操作
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                hook.callback();
            }
        });
    }
}
