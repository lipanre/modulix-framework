package com.modulix.framework.mybatis.plus.api.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.BooleanUtils;

/**
 * 分页信息
 *
 * @author lipanre
 */
public class PageContextHolder {

    private static final ThreadLocal<Page<Object>> pageRequestInfoThreadLocal = new ThreadLocal<>();

    private static final ThreadLocal<Boolean> pageAbleThreadLocal = new ThreadLocal<>();


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
    public static void setPageAble(Boolean pageAble) {
        pageAbleThreadLocal.set(pageAble);
    }

    /**
     * 获取是否分页
     * @return 是否分页
     */
    public static Boolean getPageAble() {
        return BooleanUtils.isTrue(pageAbleThreadLocal.get());
    }

    /**
     * 移除是否分页
     */
    public static void endPage() {
        pageAbleThreadLocal.remove();
    }
}
