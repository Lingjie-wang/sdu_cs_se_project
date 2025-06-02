package com.gump.hrbackend.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 员工部门视图
 *
 * @author jzq
 */
@Data
public class EmpDeptChartVO implements Serializable {

    private String deptName;

    private Integer empCount;

    private static final long serialVersionUID = 1L;
}