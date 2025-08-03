package com.modulix.framework.mybatis.plus.api.base;

import com.baomidou.mybatisplus.annotation.*;
import com.modulix.framework.common.core.util.TreeNode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

/**
 * 基本实体类
 *
 * @author lipanre
 */
@EqualsAndHashCode(callSuper = true)
@Data
@FieldNameConstants
public class BaseDomain extends TreeNode<Long> {

    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 创建者id
     */
    @TableField(fill = FieldFill.INSERT)
    private Long creatorId;

    /**
     * 修改者id
     */
    @TableField(fill = FieldFill.UPDATE)
    private Long modifierId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime modifyTime;

    /**
     * 删除标记
     */
    @TableLogic(value = "false", delval = "true")
    private Boolean deleted;

}
