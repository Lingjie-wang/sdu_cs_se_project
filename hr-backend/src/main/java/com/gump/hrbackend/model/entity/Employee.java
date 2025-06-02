package com.gump.hrbackend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工表
 *
 * @TableName employee
 */
@TableName(value = "employee")
@Data
public class Employee implements Serializable {
    /**
     * 员工编号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 员工姓名
     */
    private String empName;

    /**
     * 员工性别 枚举类型 0 - 男 1 - 女
     */
    private Integer empGender;

    /**
     * 员工邮箱
     */
    private String empEmail;

    /**
     * 员工电话
     */
    private String empPhone;

    /**
     * 级别编号
     */
    private Long postId;

    @TableField(exist = false)
    private String postTitle;

    /**
     * 部门编号
     */
    private Long deptId;

    @TableField(exist = false)
    private String deptName;

    /**
     * 员工入职时间
     */
    private Date hireDate;

    /**
     * 员工离职时间
     */
    private Date resignDate;

    /**
     * 工作状态 逻辑删除 枚举类型
     */
    private Integer empStatus;

    @TableField(exist = false)
    private Integer empCount;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}