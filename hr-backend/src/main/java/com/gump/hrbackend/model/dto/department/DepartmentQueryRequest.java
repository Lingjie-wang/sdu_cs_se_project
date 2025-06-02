package com.gump.hrbackend.model.dto.department;

import com.gump.hrbackend.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 *
 * @author jzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DepartmentQueryRequest extends PageRequest implements Serializable {

    /**
     * 部门
     */
    private String name;

    private static final long serialVersionUID = 1L;
}
