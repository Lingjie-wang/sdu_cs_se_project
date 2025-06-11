package com.gump.hrbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gump.hrbackend.common.BaseResponse;
import com.gump.hrbackend.common.DeleteRequest;
import com.gump.hrbackend.common.ErrorCode;
import com.gump.hrbackend.common.ResultUtils;
import com.gump.hrbackend.exception.BusinessException;
import com.gump.hrbackend.model.dto.salary.SalaryAddRequest;
import com.gump.hrbackend.model.dto.salary.SalaryQueryRequest;
import com.gump.hrbackend.model.dto.salary.SalaryUpdateRequest;
import com.gump.hrbackend.model.entity.Employee;
import com.gump.hrbackend.model.entity.Position;
import com.gump.hrbackend.model.entity.Salary;
import com.gump.hrbackend.model.entity.User;
import com.gump.hrbackend.service.EmployeeService;
import com.gump.hrbackend.service.PositionService;
import com.gump.hrbackend.service.SalaryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.gump.hrbackend.contant.UserConstant.ADMIN_ROLE;
import static com.gump.hrbackend.contant.UserConstant.USER_LOGIN_STATE;

/**
 * 员工工资接口
 *
 * @author jzq
 */
@RestController
@RequestMapping("/salary")
public class SalaryController {
    @Resource
    private EmployeeService employeeService;

    @Resource
    private PositionService positionService;

    @Resource
    private SalaryService salaryService;

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
     * @param salaryAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addSalary(@RequestBody SalaryAddRequest salaryAddRequest,
                                        HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (salaryAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Salary salary = new Salary();
        BeanUtils.copyProperties(salaryAddRequest, salary);
        boolean result = salaryService.addSalary(salary);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(salary.getId());
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteSalary(@RequestBody DeleteRequest deleteRequest,
                                              HttpServletRequest request) {
        // 仅管理员可删除
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = salaryService.removeById(deleteRequest.getId());
        return ResultUtils.success(result);
    }

    /**
     * 更新
     *
     * @param salaryUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateSalary(@RequestBody SalaryUpdateRequest salaryUpdateRequest,
                                              HttpServletRequest request) {
        // 仅管理员可更新
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (salaryUpdateRequest == null || salaryUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Salary salary = new Salary();
        BeanUtils.copyProperties(salaryUpdateRequest, salary);
        boolean result = salaryService.updateSalary(salary);
        return ResultUtils.success(result);
    }

    /**
     * 获取列表
     *
     * @param salaryQueryRequest
     * @return
     */
    @GetMapping("/list")
    public BaseResponse<List<Salary>> getSalaryList(SalaryQueryRequest salaryQueryRequest) {
        Salary salaryQuery = new Salary();
        if (salaryQueryRequest != null) {
            BeanUtils.copyProperties(salaryQueryRequest, salaryQuery);
        }
        QueryWrapper<Salary> salaryQueryWrapper = new QueryWrapper<>(salaryQuery);
        // 如果查询条件中包含员工名称 则需要先查询员工表 再根据员工 id 查询工资表
        if (StringUtils.isNotBlank(salaryQuery.getEmpName())) {
            QueryWrapper<Employee> employeeQueryWrapper = new QueryWrapper<>();
            employeeQueryWrapper.eq("emp_name", salaryQuery.getEmpName());
            Employee employee = employeeService.getOne(employeeQueryWrapper);
            if (employee == null) {
                throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "员工名称不存在");
            }
            salaryQueryWrapper.eq("emp_id", employee.getId()); // 根据部门 id 查询工资表
        }
        if (StringUtils.isNotBlank(salaryQuery.getPostTitle())) {
            QueryWrapper<Position> positionQueryWrapper = new QueryWrapper<>();
            positionQueryWrapper.eq("title", salaryQuery.getPostTitle());
            Position position = positionService.getOne(positionQueryWrapper);
            if (position == null) {
                throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "员工级别不存在");
            }
            salaryQueryWrapper.eq("post_id", position.getId()); // 根据级别 id 查询工资表
        }
        if (salaryQuery.getEmpId() != null) {
            salaryQueryWrapper.eq("emp_id", salaryQuery.getEmpId()); // 根据员工 id 查询工资表
        }
        if (salaryQuery.getPostId() != null) {
            salaryQueryWrapper.eq("post_id", salaryQuery.getPostId()); // 根据级别 id 查询工资表
        }
        List<Salary> salaryList = salaryService.list(salaryQueryWrapper);
        return ResultUtils.success(salaryList);
    }

}
