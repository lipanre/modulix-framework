package com.modulix.framework.common.core.util;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

/**
 * 树节点
 *
 * @author LiPan
 */
@Data
public abstract class TreeNode<T> {

    /**
     * 子节点列表
     */
    @TableField(exist = false)
    private List<TreeNode<T>> children;

    /**
     * 获取当前节点标识符
     *
     * @return 当前节点标识符
     */
    public abstract T getId();

    /**
     * 获取树节点的排序字段值
     *
     * @return 排序字段值
     */
    public Integer getSort() {
        return null;
    }

    /**
     * 获取上级节点标识符
     * <br>
     * 大部分类不是树形结构，所以返回空也不影响数据展示
     * @return 上级节点标识符
     */
    public T getParentId() {
        return null;
    }
}
