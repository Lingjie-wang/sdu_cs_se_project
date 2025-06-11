package com.gump.hrbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gump.hrbackend.model.entity.Salary;

/**
 * 员工工资 业务逻辑接口
 *
 * @author jzq
 */
public interface SalaryService extends IService<Salary> {
    /**
     * 新增员工工资
     *
     * @param salary
     * @return
     */
    boolean addSalary(Salary salary);

    /**
     * 更新员工工资
     *
     * @param salary
     * @return
     */
    boolean updateSalary(Salary salary);
}
