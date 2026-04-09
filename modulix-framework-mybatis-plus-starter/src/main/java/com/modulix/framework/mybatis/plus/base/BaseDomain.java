package com.modulix.framework.mybatis.plus.base;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

/**
 * 基本实体类
 *
 * @author lipanre
 */
@Data
@FieldNameConstants
public class BaseDomain {

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
