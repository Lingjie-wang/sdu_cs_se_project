package com.gump.hrbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gump.hrbackend.common.ErrorCode;
import com.gump.hrbackend.exception.BusinessException;
import com.gump.hrbackend.mapper.SalaryMapper;
import com.gump.hrbackend.model.entity.Employee;
import com.gump.hrbackend.model.entity.Position;
import com.gump.hrbackend.model.entity.Salary;
import com.gump.hrbackend.service.EmployeeService;
import com.gump.hrbackend.service.PositionService;
import com.gump.hrbackend.service.SalaryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 员工工资 业务逻辑实现类
 *
 * @author jzq
 */
@Service
public class SalaryServiceImpl extends ServiceImpl<SalaryMapper, Salary>
        implements SalaryService {

    @Resource
    private SalaryMapper salaryMapper;

    @Resource
    private EmployeeService employeeService;

    @Resource
    private PositionService positionService;

    /**
     * 新增员工工资
     *
     * @param salary
     * @return
     */
    @Override
    public boolean addSalary(Salary salary) {
        Salary salaryToAdd = new Salary();
        salaryToAdd.setEmpName(salary.getEmpName());
        salaryToAdd.setWage(salary.getWage());
        salaryToAdd.setBonus(salary.getBonus());
        // 校验员工姓名
        QueryWrapper<Employee> employeeQueryWrapper = new QueryWrapper<>();
        employeeQueryWrapper.eq("emp_name", salary.getEmpName());
        Employee employee = employeeService.getOne(employeeQueryWrapper);
        if (employee == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "员工不存在");
        }
        salaryToAdd.setEmpId(employee.getId());
        // 校验级别名称
        QueryWrapper<Position> positionQueryWrapper = new QueryWrapper<>();
        positionQueryWrapper.eq("title", salary.getPostTitle());
        Position position = positionService.getOne(positionQueryWrapper);
        if (position == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "员工级别不存在");
        }
        salaryToAdd.setPostId(position.getId());
        boolean saveResult = this.save(salaryToAdd);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "创建培训记录失败，数据库错误");
        }
        return true;
    }

    /**
     * 更新员工工资
     *
     * @param salary
     * @return
     */
    @Override
    public boolean updateSalary(Salary salary) {
        Salary salaryToUpdate = new Salary();
        salaryToUpdate.setId(salary.getId());
        if (salary.getWage() != null) {
            salaryToUpdate.setWage(salary.getWage());
        }
        if (salary.getBonus() != null) {
            salaryToUpdate.setBonus(salary.getBonus());
        }
        if (salary.getCreateTime() != null) {
            salaryToUpdate.setCreateTime(salary.getCreateTime());
        }
        return salaryMapper.updateById(salaryToUpdate) > 0;
    }
}



