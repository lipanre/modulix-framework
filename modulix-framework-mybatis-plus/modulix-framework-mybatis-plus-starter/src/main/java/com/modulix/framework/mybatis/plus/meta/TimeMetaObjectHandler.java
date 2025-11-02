package com.modulix.framework.mybatis.plus.meta;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.modulix.framework.mybatis.plus.api.base.BaseDomain;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * 实体类时间信息字段处理器
 *
 * <br>
 * {@code date} 2025/2/17 12:06
 */
public class TimeMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, BaseDomain.Fields.createTime, LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, BaseDomain.Fields.modifyTime, LocalDateTime.class, LocalDateTime.now());
    }
}