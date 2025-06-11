package com.gump.hrbackend.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 考勤视图
 *
 * @author jzq
 */
@Data
public class AttendanceChartVO implements Serializable {

    /**
     * 考勤类型
     */
    private Integer attendanceType;

    /**
     * 考勤类型名称
     */
    private String attendanceTypeName;

    /**
     * 考勤次数
     */

    private Integer attendanceCount;

    private static final long serialVersionUID = 1L;

    // 为 attendanceTypeName 赋值
    public void setAttendanceTypeName(Integer attendanceType) {
        switch (attendanceType) {
            case 0:
                this.attendanceTypeName = "正常";
                break;
            case 1:
                this.attendanceTypeName = "迟到";
                break;
            case 2:
                this.attendanceTypeName = "缺勤";
                break;
            default:
                this.attendanceTypeName = "请假";
                break;
        }
    }
}
