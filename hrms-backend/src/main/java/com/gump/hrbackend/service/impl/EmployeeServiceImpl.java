package com.gump.hrbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gump.hrbackend.common.ErrorCode;
import com.gump.hrbackend.exception.BusinessException;
import com.gump.hrbackend.mapper.EmployeeMapper;
import com.gump.hrbackend.model.entity.Department;
import com.gump.hrbackend.model.entity.Employee;
import com.gump.hrbackend.model.entity.Position;
import com.gump.hrbackend.service.DepartmentService;
import com.gump.hrbackend.service.EmployeeService;
import com.gump.hrbackend.service.PositionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 员工 业务逻辑实现类
 *
 * @author jzq
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
        implements EmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;

    @Resource
    private DepartmentService departmentService;

    @Resource
    private PositionService positionService;

    /**
     * 新增员工
     *
     * @param employee
     * @return
     */
    @Override
    public boolean addEmployee(Employee employee) {
        Employee employeeToAdd = new Employee();
        employeeToAdd.setEmpName(employee.getEmpName());
        employeeToAdd.setEmpGender(employee.getEmpGender());
        employeeToAdd.setEmpEmail(employee.getEmpEmail());
        employeeToAdd.setEmpPhone(employee.getEmpPhone());
        employeeToAdd.setHireDate(employee.getHireDate());
        employeeToAdd.setResignDate(employee.getResignDate());
        employeeToAdd.setEmpStatus(employee.getEmpStatus());
        // 校验部门名称
        QueryWrapper<Department> departmentQueryWrapper = new QueryWrapper<>();
        departmentQueryWrapper.eq("name", employee.getDeptName());
        Department department = departmentService.getOne(departmentQueryWrapper);
        if (department == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "员工部门不存在");
        }
        employeeToAdd.setDeptId(department.getId());
        // 校验级别名称
        QueryWrapper<Position> positionQueryWrapper = new QueryWrapper<>();
        positionQueryWrapper.eq("title", employee.getPostTitle());
        Position position = positionService.getOne(positionQueryWrapper);
        if (position == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "员工级别不存在");
        }
        employeeToAdd.setPostId(position.getId());
        boolean saveResult = this.save(employeeToAdd);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "创建员工失败，数据库错误");
        }
        return true;
    }

    /**
     * 更新员工
     *
     * @param employee
     * @return
     */
    @Override
    public boolean updateEmployee(Employee employee) {
        Employee employeeToUpdate = new Employee();
        employeeToUpdate.setId(employee.getId());
        if (employee.getEmpGender() != null) {
            employeeToUpdate.setEmpGender(employee.getEmpGender());
        }
        employeeToUpdate.setHireDate(employee.getHireDate());
        if (employee.getResignDate() != null) {
            employeeToUpdate.setResignDate(employee.getResignDate());
            employeeToUpdate.setEmpStatus(1);
        }
        // 校验
        if (StringUtils.isNotBlank(employee.getEmpName())) {
            employeeToUpdate.setEmpName(employee.getEmpName());
        }
        if (StringUtils.isNotBlank(employee.getEmpEmail())) {
            employeeToUpdate.setEmpEmail(employee.getEmpEmail());
        }
        if (StringUtils.isNotBlank(employee.getEmpPhone())) {
            employeeToUpdate.setEmpPhone(employee.getEmpPhone());
        }
        // 校验部门名称
        QueryWrapper<Department> departmentQueryWrapper = new QueryWrapper<>();
        departmentQueryWrapper.eq("name", employee.getDeptName());
        Department department = departmentService.getOne(departmentQueryWrapper);
        if (department == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "员工部门不存在");
        }
        employeeToUpdate.setDeptId(department.getId());
        // 校验级别名称
        QueryWrapper<Position> positionQueryWrapper = new QueryWrapper<>();
        positionQueryWrapper.eq("title", employee.getPostTitle());
        Position position = positionService.getOne(positionQueryWrapper);
        if (position == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "员工级别不存在");
        }
        employeeToUpdate.setPostId(position.getId());
        return employeeMapper.updateById(employeeToUpdate) > 0;
    }
}




