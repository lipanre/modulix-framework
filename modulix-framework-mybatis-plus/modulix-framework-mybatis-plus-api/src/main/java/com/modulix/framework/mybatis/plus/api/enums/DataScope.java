package com.modulix.framework.mybatis.plus.api.enums;

/**
 * 数据权限类型
 *
 * <br>
 * {@code date} 2025/3/4 12:11
 */
public class DataScope {

    /**
     * 当前部门
     */
    public static final String dept = "dept";

    /**
     * 当前部门及子部门
     */
    public static final String deptAndChild = "dept_and_child";

    /**
     * 自定义
     */
    public static final String customize = "customize";

    /**
     * 仅本人
     */
    public static final String self = "self";

}
