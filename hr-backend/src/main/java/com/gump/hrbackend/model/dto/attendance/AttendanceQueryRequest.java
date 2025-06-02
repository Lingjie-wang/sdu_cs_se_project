package com.gump.hrbackend.model.dto.attendance;

import com.gump.hrbackend.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工考勤查询请求
 *
 * @author jzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AttendanceQueryRequest extends PageRequest implements Serializable {
    /**
     * 员工编号
     */
    private Long empId;

    /**
     * 员工姓名
     */
    private String empName;

    /**
     * 考勤日期
     */
    private Date attendanceDate;

    /**
     * 考勤类型 0 - 正常 1 - 迟到 2 - 缺勤 3 - 请假
     */
    private Integer attendanceType;

    private static final long serialVersionUID = 1L;
}
