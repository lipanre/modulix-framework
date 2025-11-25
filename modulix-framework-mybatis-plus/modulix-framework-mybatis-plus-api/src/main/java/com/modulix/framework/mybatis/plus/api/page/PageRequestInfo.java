package com.modulix.framework.mybatis.plus.api.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * 分页请求信息
 *
 * @author lipanre
 */
@Data
public class PageRequestInfo<T> {

    /**
     * 分页的page对象
     */
    private Page<T> page;
}
