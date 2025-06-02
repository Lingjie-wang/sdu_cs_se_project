package com.gump.hrbackend.model.dto.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工更新请求
 *
 * @author jzq
 */
@Data
public class EmployeeUpdateRequest implements Serializable {
    /**
     * 员工编号
     */
    private Long id;

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
     * 员工离职时间 加上 @JsonFormat 注解解析日期类型
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date resignDate;

    /**
     * 工作状态 逻辑删除 枚举类型
     */
    private Integer empStatus;

    private static final long serialVersionUID = 1L;
}