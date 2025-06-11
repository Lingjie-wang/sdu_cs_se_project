package com.gump.hrbackend.model.dto.training;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工培训更新请求
 *
 * @author jzq
 */
@Data
public class TrainingUpdateRequest implements Serializable {
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
     * 培训日期 加上 @JsonFormat 注解解析日期类型
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
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
