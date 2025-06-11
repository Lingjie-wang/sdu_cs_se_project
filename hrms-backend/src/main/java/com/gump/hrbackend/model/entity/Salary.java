package com.gump.hrbackend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工工资表
 *
 * @TableName salary
 */
@TableName(value = "salary")
@Data
public class Salary implements Serializable {
    /**
     * 工资编号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 员工编号
     */
    private Long empId;

    /**
     * 员工姓名
     */
    private String empName;

    /**
     * 员工级别
     */
    private Long postId;

    @TableField(exist = false)
    private String postTitle;

    /**
     * 基本工资
     */
    private Integer wage;

    /**
     * 奖金
     */
    private Integer bonus;

    /**
     * 工资发放时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}