package com.gump.hrbackend.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 员工职位视图
 *
 * @author jzq
 */
@Data
public class EmpPostChartVO implements Serializable {

    private String postTitle;

    private Integer empCount;

    private static final long serialVersionUID = 1L;
}