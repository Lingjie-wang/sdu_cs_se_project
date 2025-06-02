package com.gump.hrbackend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 级别表
 *
 * @TableName position
 */
@TableName(value = "position")
@Data
public class Position implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 级别名称
     */
    private String title;

    /**
     * 级别职责
     */
    private String duty;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}