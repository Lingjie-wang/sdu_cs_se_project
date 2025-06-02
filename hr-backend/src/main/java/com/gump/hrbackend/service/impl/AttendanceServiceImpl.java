package com.gump.hrbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gump.hrbackend.common.ErrorCode;
import com.gump.hrbackend.exception.BusinessException;
import com.gump.hrbackend.mapper.AttendanceMapper;
import com.gump.hrbackend.model.entity.Attendance;
import com.gump.hrbackend.model.entity.Employee;
import com.gump.hrbackend.service.AttendanceService;
import com.gump.hrbackend.service.EmployeeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 员工考勤 业务逻辑实现类
 *
 * @author jzq
 */
@Service
public class AttendanceServiceImpl extends ServiceImpl<AttendanceMapper, Attendance>
        implements AttendanceService {

    @Resource
    private AttendanceMapper attendanceMapper;

    @Resource
    private EmployeeService employeeService;


    /**
     * 新增员工工资
     *
     * @param attendance
     * @return
     */
    @Override
    public boolean addAttendance(Attendance attendance) {
        Attendance attendanceToAdd = new Attendance();
        attendanceToAdd.setEmpName(attendance.getEmpName());
        attendanceToAdd.setAttendanceType(attendance.getAttendanceType());
        if (attendance.getAttendanceDate() != null) {
            attendanceToAdd.setAttendanceDate(attendance.getAttendanceDate());
        }
        // 校验员工姓名
        QueryWrapper<Employee> employeeQueryWrapper = new QueryWrapper<>();
        employeeQueryWrapper.eq("emp_name", attendance.getEmpName());
        Employee employee = employeeService.getOne(employeeQueryWrapper);
        if (employee == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "员工不存在");
        }
        attendanceToAdd.setEmpId(employee.getId());
        boolean saveResult = this.save(attendanceToAdd);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "创建员工考勤记录失败，数据库错误");
        }
        return true;
    }

    /**
     * 更新员工工资
     *
     * @param attendance
     * @return
     */
    @Override
    public boolean updateAttendance(Attendance attendance) {
        Attendance attendanceToUpdate = new Attendance();
        attendanceToUpdate.setId(attendance.getId());
        if (attendance.getAttendanceDate() != null) {
            attendanceToUpdate.setAttendanceDate(attendance.getAttendanceDate());
        }
        if (attendance.getAttendanceType() != null) {
            attendanceToUpdate.setAttendanceType(attendance.getAttendanceType());
        }
        return attendanceMapper.updateById(attendanceToUpdate) > 0;
    }
}




