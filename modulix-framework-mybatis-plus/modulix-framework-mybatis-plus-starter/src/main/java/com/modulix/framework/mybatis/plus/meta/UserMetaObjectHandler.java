package com.modulix.framework.mybatis.plus.meta;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.modulix.framework.mybatis.plus.api.base.BaseDomain;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;

/**
 * 实体类用户信息字段处理器
 *
 * <br>
 * {@code date} 2025/2/17 12:06
 */
public class UserMetaObjectHandler implements MetaObjectHandler {

    @Override
    public boolean openInsertFill(MappedStatement mappedStatement) {
        return checkLogin();
    }

    @Override
    public boolean openUpdateFill(MappedStatement mappedStatement) {
        return checkLogin();
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, BaseDomain.Fields.creatorId, Long.class, StpUtil.getLoginIdAsLong());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, BaseDomain.Fields.modifierId, Long.class, StpUtil.getLoginIdAsLong());
    }

    /**
     * 检查是否登录
     *
     * @return true - 已登录 <br>
     *         false - 未登录
     */
    private static boolean checkLogin() {
        try {
            return StpUtil.isLogin();
        } catch (Exception e) {
            return false;
        }
    }
}