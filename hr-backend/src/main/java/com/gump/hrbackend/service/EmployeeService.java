package com.gump.hrbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gump.hrbackend.model.entity.Employee;

/**
 * 员工 业务逻辑接口
 *
 * @author jzq
 */
public interface EmployeeService extends IService<Employee> {
    /**
     * 新增员工
     *
     * @param employee
     * @return
     */
    boolean addEmployee(Employee employee);

    /**
     * 更新员工
     *
     * @param employee
     * @return
     */
    boolean updateEmployee(Employee employee);
}
