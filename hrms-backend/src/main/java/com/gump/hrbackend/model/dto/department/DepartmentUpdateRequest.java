package com.gump.hrbackend.model.dto.department;

import lombok.Data;

import java.io.Serializable;

/**
 * 部门更新请求
 *
 * @author jzq
 */
@Data
public class DepartmentUpdateRequest implements Serializable {
    /**
     * 部门 id
     */
    private Long id;

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
