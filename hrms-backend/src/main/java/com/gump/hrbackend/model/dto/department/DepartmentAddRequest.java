package com.gump.hrbackend.model.dto.department;

import lombok.Data;

import java.io.Serializable;

/**
 * 部门创建请求
 *
 * @author jzq
 */
@Data
public class DepartmentAddRequest implements Serializable {

    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门职责
     */
    private String responsibility;

    private static final long serialVersionUID = 1L;
}