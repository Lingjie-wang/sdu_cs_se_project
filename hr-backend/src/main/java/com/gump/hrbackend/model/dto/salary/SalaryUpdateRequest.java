package com.gump.hrbackend.model.dto.salary;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工工资更新请求
 *
 * @author jzq
 */
@Data
public class SalaryUpdateRequest implements Serializable {
    /**
     * 工资编号
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
     * 员工级别
     */
    private Long postId;

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
     * 工资发放时间 加上 @JsonFormat 注解解析日期类型
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}
