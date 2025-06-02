package com.gump.hrbackend.model.dto.training;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工培训创建请求
 *
 * @author jzq
 */
@Data
public class TrainingAddRequest implements Serializable {
    /**
     * 培训编号
     */
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

    private static final long serialVersionUID = 1L;
}
