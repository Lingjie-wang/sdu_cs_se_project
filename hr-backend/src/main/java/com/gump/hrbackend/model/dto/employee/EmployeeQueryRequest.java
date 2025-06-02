package com.gump.hrbackend.model.dto.employee;

import com.gump.hrbackend.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工查询请求
 *
 * @author jzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EmployeeQueryRequest extends PageRequest implements Serializable {
    /**
     * 员工姓名
     */
    private String empName;

    /**
     * 员工性别 枚举类型 0 - 男 1 - 女
     */
    private Integer empGender;

    /**
     * 员工邮箱
     */
    private String empEmail;

    /**
     * 员工电话
     */
    private String empPhone;

    /**
     * 级别编号
     */
    private Long postId;

    private String postTitle;

    /**
     * 部门编号
     */
    private Long deptId;

    private String deptName;

    /**
     * 员工入职时间
     */
    private Date hireDate;

    /**
     * 员工离职时间
     */
    private Date resignDate;

    /**
     * 工作状态 逻辑删除 枚举类型
     */
    private Integer empStatus;

    private static final long serialVersionUID = 1L;
}