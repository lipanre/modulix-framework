package com.modulix.framework.mybatis.plus.api.page;

/**
 * 分页信息
 *
 * @author lipanre
 */
public class PageContextHolder {

    private static final ThreadLocal<PageRequestInfo<Object>> pageRequestInfoThreadLocal = new ThreadLocal<>();


    /**
     * 设置分页信息
     *
     * @param pageRequestInfo 分页信息
     */
    public static void setPageRequestInfo(PageRequestInfo<Object> pageRequestInfo) {
        pageRequestInfoThreadLocal.set(pageRequestInfo);
    }

    /**
     * 获取分页信息
     * @return 分页信息
     */
    public static PageRequestInfo<Object> getPageRequestInfo() {
        return pageRequestInfoThreadLocal.get();
    }

    /**
     * 移除分页信息
     */
    public static void removePageRequestInfo() {
        pageRequestInfoThreadLocal.remove();
    }
}
