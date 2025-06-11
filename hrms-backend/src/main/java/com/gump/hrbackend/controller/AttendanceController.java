package com.gump.hrbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.gump.hrbackend.common.BaseResponse;
import com.gump.hrbackend.common.DeleteRequest;
import com.gump.hrbackend.common.ErrorCode;
import com.gump.hrbackend.common.ResultUtils;
import com.gump.hrbackend.exception.BusinessException;
import com.gump.hrbackend.model.dto.attendance.AttendanceAddRequest;
import com.gump.hrbackend.model.dto.attendance.AttendanceQueryRequest;
import com.gump.hrbackend.model.dto.attendance.AttendanceUpdateRequest;
import com.gump.hrbackend.model.entity.Attendance;
import com.gump.hrbackend.model.entity.Employee;
import com.gump.hrbackend.model.entity.User;
import com.gump.hrbackend.service.AttendanceService;
import com.gump.hrbackend.service.EmployeeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

import static com.gump.hrbackend.contant.UserConstant.ADMIN_ROLE;
import static com.gump.hrbackend.contant.UserConstant.USER_LOGIN_STATE;

/**
 * 员工考勤接口
 *
 * @author jzq
 */
@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    @Resource
    private EmployeeService employeeService;

    @Resource
    private AttendanceService attendanceService;

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }

    /**
     * 创建
     *
     * @param attendanceAddRequest
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addAttendance(@RequestBody AttendanceAddRequest attendanceAddRequest) {
        if (attendanceAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Attendance attendance = new Attendance();
        BeanUtils.copyProperties(attendanceAddRequest, attendance);
        boolean result = attendanceService.addAttendance(attendance);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(attendance.getId());
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteAttendance(@RequestBody DeleteRequest deleteRequest,
                                                  HttpServletRequest request) {
        // 仅管理员可删除
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = attendanceService.removeById(deleteRequest.getId());
        return ResultUtils.success(result);
    }

    /**
     * 更新
     *
     * @param attendanceUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateAttendance(@RequestBody AttendanceUpdateRequest attendanceUpdateRequest,
                                                  HttpServletRequest request) {
        // 仅管理员可更新
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (attendanceUpdateRequest == null || attendanceUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Attendance attendance = new Attendance();
        BeanUtils.copyProperties(attendanceUpdateRequest, attendance);
        boolean result = attendanceService.updateAttendance(attendance);
        return ResultUtils.success(result);
    }

    /**
     * 获取列表
     *
     * @param attendanceQueryRequest
     * @return
     */
    @GetMapping("/list")
    public BaseResponse<List<Attendance>> getAttendanceList(AttendanceQueryRequest attendanceQueryRequest) {
        Attendance attendanceQuery = new Attendance();
        if (attendanceQueryRequest != null) {
            BeanUtils.copyProperties(attendanceQueryRequest, attendanceQuery);
        }
        QueryWrapper<Attendance> attendanceQueryWrapper = new QueryWrapper<>(attendanceQuery);
        // 如果查询条件中包含员工名称 则需要先查询员工表 再根据员工 id 查询工资表
        if (StringUtils.isNotBlank(attendanceQuery.getEmpName())) {
            QueryWrapper<Employee> employeeQueryWrapper = new QueryWrapper<>();
            employeeQueryWrapper.eq("emp_name", attendanceQuery.getEmpName());
            Employee employee = employeeService.getOne(employeeQueryWrapper);
            if (employee == null) {
                throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "员工名称不存在");
            }
            attendanceQueryWrapper.eq("emp_id", employee.getId()); // 根据部门 id 查询考勤表
        }
        if (attendanceQuery.getEmpId() != null) {
            attendanceQueryWrapper.eq("emp_id", attendanceQuery.getEmpId()); // 根据员工 id 查询考勤表
        }
        List<Attendance> attendanceList = attendanceService.list(attendanceQueryWrapper);
        // 根据考勤日期排序
        attendanceList.sort(Comparator.comparing(Attendance::getAttendanceDate));
        return ResultUtils.success(attendanceList);
    }

}
