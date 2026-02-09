package com.modulix.framework.mybatis.plus.api.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Objects;

/**
 * 分页信息
 *
 * @author lipanre
 */
public class PageContextHolder {

    private static final ThreadLocal<Page<Object>> pageRequestInfoThreadLocal = new ThreadLocal<>();

    private static final ThreadLocal<String> pageStatementThreadLocal = new ThreadLocal<>();


    /**
     * 设置分页信息
     *
     * @param pageRequestInfo 分页信息
     */
    public static void setPageRequestInfo(Page<Object> pageRequestInfo) {
        pageRequestInfoThreadLocal.set(pageRequestInfo);
    }

    /**
     * 获取分页信息
     * @return 分页信息
     */
    public static Page<Object> getPageRequestInfo() {
        return pageRequestInfoThreadLocal.get();
    }

    /**
     * 移除分页信息
     */
    public static void removePageRequestInfo() {
        pageRequestInfoThreadLocal.remove();
    }

    /**
     * 设置是否分页
     */
    public static void setPageAble(String statementId) {
        pageStatementThreadLocal.set(statementId);
    }

    /**
     * 获取是否分页
     * @return 是否分页
     */
    public static Boolean getPageAble(String statementId) {
        return Objects.equals(statementId, pageStatementThreadLocal.get());
    }

    /**
     * 移除是否分页
     */
    public static void endPage() {
        pageStatementThreadLocal.remove();
    }
}
