package com.gump.hrbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.gump.hrbackend.common.BaseResponse;
import com.gump.hrbackend.common.ResultUtils;
import com.gump.hrbackend.mapper.AttendanceMapper;
import com.gump.hrbackend.mapper.EmployeeMapper;
import com.gump.hrbackend.model.entity.Attendance;
import com.gump.hrbackend.model.entity.Department;
import com.gump.hrbackend.model.entity.Employee;
import com.gump.hrbackend.model.entity.Position;
import com.gump.hrbackend.model.vo.AttendanceChartVO;
import com.gump.hrbackend.model.vo.EmpDeptChartVO;
import com.gump.hrbackend.model.vo.EmpPostChartVO;
import com.gump.hrbackend.service.AttendanceService;
import com.gump.hrbackend.service.DepartmentService;
import com.gump.hrbackend.service.EmployeeService;
import com.gump.hrbackend.service.PositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 视图接口
 *
 * @author jzw
 */
@RestController
@RequestMapping("/charts")
@Slf4j
public class ChartsController {

    @Resource
    private EmployeeService employeeService;

    @Resource
    private EmployeeMapper employeeMapper;

    @Resource
    private DepartmentService departmentService;

    @Resource
    private PositionService positionService;

    @Resource
    private AttendanceMapper attendanceMapper;

    @Resource
    private AttendanceService attendanceService;

    @CrossOrigin(origins = "http://localhost:8000") // 允许来自指定域名的跨域访问
    @GetMapping("/data")
    public BaseResponse<HashMap<String, Object>> getChartsData() {
        HashMap<String, Object> map = new HashMap<>();

        QueryWrapper<Employee> employeeQueryWrapper = new QueryWrapper<>();
        employeeQueryWrapper.eq("emp_status", 0); // 设置查询条件，字段名为 "emp_status"，值为 0
        long empCount = employeeService.count(employeeQueryWrapper);
        map.put("empCount", empCount);

        QueryWrapper<Department> departmentQueryWrapper = new QueryWrapper<>();
        long deptCount = departmentService.count(departmentQueryWrapper);
        map.put("deptCount", deptCount);

        QueryWrapper<Position> positionQueryWrapper = new QueryWrapper<>();
        long postCount = positionService.count(positionQueryWrapper);
        map.put("postCount", postCount);

        QueryWrapper<Attendance> attendanceQueryWrapper = new QueryWrapper<>();
        long attendanceCount = attendanceService.count(attendanceQueryWrapper);
        map.put("attendanceCount", attendanceCount);

        // 与 department 表联合查询
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("emp_status", 0); // 设置查询条件，emp_status字段值为0
        queryWrapper.select("count(*) as empCount, dept_id");
        queryWrapper.groupBy("dept_id");
        List<Employee> employeeList = employeeMapper.selectList(queryWrapper);

        List<EmpDeptChartVO> empDeptChartVOList = employeeList.stream().map(employee -> {
            EmpDeptChartVO empDeptChartVO = new EmpDeptChartVO();
            QueryWrapper<Department> wrapper = new QueryWrapper<>();
            wrapper.eq("id", employee.getDeptId());
            Department department = departmentService.getOne(wrapper);
            employee.setDeptName(department.getName());
            BeanUtils.copyProperties(employee, empDeptChartVO);
            return empDeptChartVO;
        }).collect(Collectors.toList());
        map.put("empDeptChart", empDeptChartVOList);

        // 与 position 表联合查询
        QueryWrapper<Employee> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("emp_status", 0); // 设置查询条件，emp_status字段值为0
        queryWrapper1.select("count(*) as empCount, post_id");
        queryWrapper1.groupBy("post_id");
        List<Employee> employeeList1 = employeeMapper.selectList(queryWrapper1);

        List<EmpPostChartVO> EmpPostChartVOList = employeeList1.stream().map(employee -> {
            EmpPostChartVO empPostChartVO = new EmpPostChartVO();
            QueryWrapper<Position> wrapper = new QueryWrapper<>();
            wrapper.eq("id", employee.getPostId());
            Position position = positionService.getOne(wrapper);
            employee.setPostTitle(position.getTitle());
            BeanUtils.copyProperties(employee, empPostChartVO);
            return empPostChartVO;
        }).collect(Collectors.toList());
        map.put("empPostChart", EmpPostChartVOList);

        // 与 attendance 表联合查询
        QueryWrapper<Attendance> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.select("count(*) as attendanceCount, attendance_type");
        queryWrapper2.groupBy("attendance_type");
        List<Attendance> attendanceList = attendanceMapper.selectList(queryWrapper2);
        List<AttendanceChartVO> attendanceChartVOList = attendanceList.stream().map(attendance -> {
            AttendanceChartVO attendanceChartVO = new AttendanceChartVO();
            BeanUtils.copyProperties(attendance, attendanceChartVO);
            attendanceChartVO.setAttendanceTypeName(attendance.getAttendanceType());
            return attendanceChartVO;
        }).collect(Collectors.toList());
        map.put("attendanceChart", attendanceChartVOList);

        return ResultUtils.success(map);
    }
}
