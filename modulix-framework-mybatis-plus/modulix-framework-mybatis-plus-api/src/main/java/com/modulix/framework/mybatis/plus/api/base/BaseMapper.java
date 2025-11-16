package com.modulix.framework.mybatis.plus.api.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.github.yulichang.base.MPJBaseMapper;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * 基本mapper
 *
 * @author lipanre
 */
public interface BaseMapper<T extends BaseDomain> extends MPJBaseMapper<T> {

    /**
     * 查询条件构造器
     * <p>
     *
     * @return QueryWrapper
     */
    default Wrapper<T> queryWrapper() {
        return queryWrapper(false);
    }

    /**
     * 查询条件构造器
     * <p>
     *
     * @param isDeleted 是否删除
     * @return QueryWrapper
     */
    default Wrapper<T> queryWrapper(boolean isDeleted) {
        QueryWrapper<T> query = Wrappers.query();
        query.eq(BaseDomain.Fields.deleted, isDeleted);
        return query;
    }

    /**
     * 查询条件构造器
     * <p>
     *
     * @param entityClass 实体类
     * @return QueryWrapper
     */
    default LambdaQueryWrapper<T> lambdaQueryWrapper(Class<T> entityClass) {
        return lambdaQueryWrapper(entityClass, false);
    }

    /**
     * 查询条件构造器
     * <p>
     *
     * @param entityClass 实体类
     * @param isDeleted   是否删除
     * @return QueryWrapper
     */
    default LambdaQueryWrapper<T> lambdaQueryWrapper(Class<T> entityClass, boolean isDeleted) {
        LambdaQueryWrapper<T> wrapper = Wrappers.lambdaQuery(entityClass);
        wrapper.eq(T::getDeleted, isDeleted);
        return wrapper;
    }

    /**
     * 查询单条数据的单个字段
     * <p>
     *
     * @param columnFunction 字段函数
     * @param <R>            字段类型
     * @return 字段值
     */
    default <R> R selectColumn(Wrapper<T> wrapper, Function<T, R> columnFunction) {
        T result = selectOne(wrapper);
        return Objects.isNull(result) ? null : columnFunction.apply(result);
    }

    /**
     * 查询符合条件的多条数据的单个字段
     *
     * @param wrapper 查询条件
     * @param columnFunction 字段映射函数
     * @return 字段值列表
     * @param <R> 字段类型
     */
    default <R> List<R> selectColumnList(Wrapper<T> wrapper, Function<T, R> columnFunction) {
        List<T> result = selectList(wrapper);
        return result.stream().map(columnFunction).toList();
    }

    /**
     * 通过ID更新指定列
     *
     * @param id     实体ID
     * @param column 列函数
     * @param value  值
     * @param <COL>  列类型
     * @return 是否更新成功
     */
    default <COL> Boolean updateColumn(Long id, SFunction<T, COL> column, COL value) {
        return updateColumn(BaseDomain::getId, id, column, value);
    }

    /**
     * 更新指定列
     *
     * @param conditionFunction 条件函数
     * @param conditionValue    条件值
     * @param column            列函数
     * @param value             值
     * @param <CON>             条件类型
     * @param <COL>             列类型
     * @return 是否更新成功
     */
    @SuppressWarnings("unchecked")
    default <CON, COL> Boolean updateColumn(SFunction<T, CON> conditionFunction, CON conditionValue, SFunction<T, COL> column, COL value) {
        Class<T> entityClass = (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), BaseMapper.class, 0);
        LambdaUpdateWrapper<T> wrapper = Wrappers.lambdaUpdate(entityClass);
        wrapper.eq(conditionFunction, conditionValue);
        wrapper.set(column, value);
        return update(wrapper) > 0;
    }

}
