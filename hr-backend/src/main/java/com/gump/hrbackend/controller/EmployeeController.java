package com.gump.hrbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gump.hrbackend.common.BaseResponse;
import com.gump.hrbackend.common.DeleteRequest;
import com.gump.hrbackend.common.ErrorCode;
import com.gump.hrbackend.common.ResultUtils;
import com.gump.hrbackend.exception.BusinessException;
import com.gump.hrbackend.model.dto.employee.EmployeeAddRequest;
import com.gump.hrbackend.model.dto.employee.EmployeeQueryRequest;
import com.gump.hrbackend.model.dto.employee.EmployeeUpdateRequest;
import com.gump.hrbackend.model.entity.Department;
import com.gump.hrbackend.model.entity.Employee;
import com.gump.hrbackend.model.entity.Position;
import com.gump.hrbackend.model.entity.User;
import com.gump.hrbackend.service.DepartmentService;
import com.gump.hrbackend.service.EmployeeService;
import com.gump.hrbackend.service.PositionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

import static com.gump.hrbackend.contant.UserConstant.ADMIN_ROLE;
import static com.gump.hrbackend.contant.UserConstant.USER_LOGIN_STATE;

/**
 * 员工接口
 *
 * @author jzq
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;

    @Resource
    private PositionService positionService;

    @Resource
    private DepartmentService departmentService;

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
     * @param employeeAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addEmployee(@RequestBody EmployeeAddRequest employeeAddRequest,
                                          HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (employeeAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeAddRequest, employee);
        boolean result = employeeService.addEmployee(employee);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(employee.getId());
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteEmployee(@RequestBody DeleteRequest deleteRequest,
                                                HttpServletRequest request) {
        // 仅管理员可删除
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 业务需求 保留员工历史信息
        Employee employee = employeeService.getById(deleteRequest.getId());
        employee.setEmpStatus(1);
        Date date = new Date(System.currentTimeMillis());
        employee.setResignDate(date);
        boolean result = employeeService.updateById(employee);
        return ResultUtils.success(result);
    }

    /**
     * 更新
     *
     * @param employeeUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateEmployee(@RequestBody EmployeeUpdateRequest employeeUpdateRequest,
                                                HttpServletRequest request) {
        // 仅管理员可更新
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (employeeUpdateRequest == null || employeeUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeUpdateRequest, employee);
        boolean result = employeeService.updateEmployee(employee);
        return ResultUtils.success(result);
    }

    /**
     * 获取列表
     *
     * @param employeeQueryRequest
     * @return
     */
    @GetMapping("/list")
    public BaseResponse<List<Employee>> getEmployeeList(EmployeeQueryRequest employeeQueryRequest) {
        Employee employeeQuery = new Employee();
        if (employeeQueryRequest != null) {
            BeanUtils.copyProperties(employeeQueryRequest, employeeQuery);
        }
        QueryWrapper<Employee> employeeQueryWrapper = new QueryWrapper<>(employeeQuery);
        // 如果查询条件中包含部门名称 则需要先查询部门表 再根据部门 id 查询员工表
        if (StringUtils.isNotBlank(employeeQuery.getDeptName())) {
            QueryWrapper<Department> departmentQueryWrapper = new QueryWrapper<>();
            departmentQueryWrapper.eq("name", employeeQuery.getDeptName());
            Department department = departmentService.getOne(departmentQueryWrapper);
            if (department == null) {
                throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "员工部门不存在");
            }
            employeeQueryWrapper.eq("dept_id", department.getId()); // 根据部门 id 查询员工表
        }
        if (StringUtils.isNotBlank(employeeQuery.getPostTitle())) {
            QueryWrapper<Position> positionQueryWrapper = new QueryWrapper<>();
            positionQueryWrapper.eq("title", employeeQuery.getPostTitle());
            Position position = positionService.getOne(positionQueryWrapper);
            if (position == null) {
                throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "员工级别不存在");
            }
            employeeQueryWrapper.eq("post_id", position.getId()); // 根据级别 id 查询员工表
        }
        if (employeeQuery.getDeptId() != null) {
            employeeQueryWrapper.eq("dept_id", employeeQuery.getDeptId()); // 根据部门 id 查询员工表
        }
        if (employeeQuery.getPostId() != null) {
            employeeQueryWrapper.eq("post_id", employeeQuery.getPostId()); // 根据级别 id 查询员工表
        }
        List<Employee> employeeList = employeeService.list(employeeQueryWrapper);
        return ResultUtils.success(employeeList);
    }

}