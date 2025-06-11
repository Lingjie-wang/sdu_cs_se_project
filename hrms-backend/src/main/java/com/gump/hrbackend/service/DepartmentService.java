package com.gump.hrbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gump.hrbackend.model.entity.Department;

/**
 * 部门 业务逻辑接口
 *
 * @author jzq
 */
public interface DepartmentService extends IService<Department> {
    /**
     * 新增部门
     *
     * @param department
     * @return
     */
    boolean addDepartment(Department department);

    /**
     * 更新部门
     *
     * @param department
     * @return
     */
    boolean updateDepartment(Department department);
}
