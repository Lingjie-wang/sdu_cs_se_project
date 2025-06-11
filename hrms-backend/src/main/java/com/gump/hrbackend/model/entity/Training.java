package com.gump.hrbackend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工培训表
 *
 * @TableName training
 */
@TableName(value = "training")
@Data
public class Training implements Serializable {
    /**
     * 培训编号
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
     * 培训项目名称
     */
    private String trainingProject;

    /**
     * 培训日期
     */
    private Date trainingDate;

    /**
     * 培训时长
     */
    private Integer trainingDuration;

    /**
     * 培训讲师
     */
    private String trainer;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}