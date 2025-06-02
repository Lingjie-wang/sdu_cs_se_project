package com.gump.hrbackend.model.dto.attendance;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工工资更新请求
 *
 * @author jzq
 */
@Data
public class AttendanceUpdateRequest implements Serializable {
    /**
     * 考勤编号
     */
    private Long id;
    /**
     * 员工编号
     */
    private Long empId;

    /**
     * 员工姓名
     */
    private String empName;

    /**
     * 考勤日期 加上 @JsonFormat 注解解析日期类型
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date attendanceDate;

    /**
     * 考勤类型 0 - 正常 1 - 迟到 2 - 缺勤 3 - 请假
     */
    private Integer attendanceType;

    private static final long serialVersionUID = 1L;
}
