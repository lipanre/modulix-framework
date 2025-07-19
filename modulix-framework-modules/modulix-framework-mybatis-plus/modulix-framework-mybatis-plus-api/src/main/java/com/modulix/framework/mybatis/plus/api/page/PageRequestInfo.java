package com.modulix.framework.mybatis.plus.api.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modulix.framework.mybatis.plus.api.annotation.PageRequest;
import lombok.Data;

/**
 * 分页请求信息
 *
 * @author lipanre
 */
@Data
public class PageRequestInfo<T> {

    /**
     * 是否能分页
     * <br>
     * 考虑到一些分页的接口再有些情况下需要请求所有数据而不需要分页，所以添加了这个参数
     * <br>
     * 当{@link PageRequest#pageNum()}和{@link PageRequest#pageSize()}同时为空时，表示不需要进行分页操作
     */
    private boolean pageable = false;

    /**
     * 分页的page对象
     */
    private Page<T> page;
}
