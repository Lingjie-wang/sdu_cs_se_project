package com.gump.hrbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gump.hrbackend.model.entity.Attendance;

/**
 * 员工考勤 业务逻辑接口
 *
 * @author jzq
 */
public interface AttendanceService extends IService<Attendance> {
    /**
     * 新增员工考勤
     *
     * @param attendance
     * @return
     */
    boolean addAttendance(Attendance attendance);

    /**
     * 更新员工考勤
     *
     * @param attendance
     * @return
     */
    boolean updateAttendance(Attendance attendance);
}
