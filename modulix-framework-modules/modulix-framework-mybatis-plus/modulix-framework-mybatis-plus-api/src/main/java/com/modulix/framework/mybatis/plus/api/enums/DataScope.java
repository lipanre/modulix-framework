package com.modulix.framework.mybatis.plus.api.enums;

/**
 * 数据权限类型
 *
 * <br>
 * {@code date} 2025/3/4 12:11
 */
public enum DataScope {

    /**
     * 当前部门
     */
    DEPT,

    /**
     * 当前部门及子部门
     */
    DEPT_AND_CHILD,

    /**
     * 自定义
     */
    CUSTOMIZE,

    /**
     * 仅本人
     */
    SELF,

}
