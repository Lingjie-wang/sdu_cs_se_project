package com.gump.hrbackend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工考勤表
 *
 * @TableName attendance
 */
@TableName(value = "attendance")
@Data
public class Attendance implements Serializable {
    /**
     * 考勤编号
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
     * 考勤日期
     */
    private Date attendanceDate;

    /**
     * 考勤类型 0 - 正常 1 - 迟到 2 - 缺勤 3 - 请假
     */
    private Integer attendanceType;

    @TableField(exist = false)
    private Integer attendanceCount;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}