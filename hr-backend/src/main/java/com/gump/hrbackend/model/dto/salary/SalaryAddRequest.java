package com.gump.hrbackend.model.dto.salary;

import lombok.Data;

import java.io.Serializable;

/**
 * 员工工资创建请求
 *
 * @author jzq
 */
@Data
public class SalaryAddRequest implements Serializable {
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

    private static final long serialVersionUID = 1L;
}