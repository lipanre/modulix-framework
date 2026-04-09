package com.modulix.framework.common.core.util;

import java.util.List;

/**
 * 树节点
 *
 * @author LiPan
 */
public interface TreeNode<T> {


    /**
     * 获取当前节点标识符
     *
     * @return 当前节点标识符
     */
    T getId();

    /**
     * 获取树节点的排序字段值
     *
     * @return 排序字段值
     */
    Integer getSort();

    /**
     * 获取上级节点标识符
     * <br>
     * 大部分类不是树形结构，所以返回空也不影响数据展示
     * @return 上级节点标识符
     */
    T getParentId();

    /**
     * 设置子节点列表
     *
     * @param children 子节点列表
     */
    void setChildren(List<TreeNode<T>> children);
}
