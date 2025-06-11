package com.gump.hrbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.gump.hrbackend.common.ErrorCode;
import com.gump.hrbackend.exception.BusinessException;
import com.gump.hrbackend.mapper.DepartmentMapper;
import com.gump.hrbackend.model.entity.Department;
import com.gump.hrbackend.service.DepartmentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 部门 业务逻辑实现类
 *
 * @author jzq
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department>
        implements DepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;

    /**
     * 新增部门
     *
     * @param department
     * @return
     */
    @Override
    public boolean addDepartment(Department department) {
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", department.getName());
        long count = departmentMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "部门名称重复");
        }
        Department departmentToAdd = new Department();
        departmentToAdd.setName(department.getName());
        departmentToAdd.setResponsibility(department.getResponsibility());
        boolean saveResult = this.save(departmentToAdd);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "创建部门失败，数据库错误");
        }
        return true;
    }

    /**
     * 更新部门
     *
     * @param department
     * @return
     */
    @Override
    public boolean updateDepartment(Department department) {
        Department departmentToUpdate = new Department();
        departmentToUpdate.setId(department.getId());
        if (StringUtils.isNotBlank(department.getName())) {
            QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name", department.getName());
            long count = departmentMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "部门名称重复");
            }
            departmentToUpdate.setName(department.getName());
        }
        if (StringUtils.isNotBlank(department.getResponsibility())) {
            departmentToUpdate.setResponsibility(department.getResponsibility());
        }
        return departmentMapper.updateById(departmentToUpdate) > 0;
    }
}




