package com.gump.hrbackend.model.dto.salary;
import com.gump.hrbackend.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工工资查询请求
 *
 * @author jzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SalaryQueryRequest extends PageRequest implements Serializable {
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
     * 工资发放时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}