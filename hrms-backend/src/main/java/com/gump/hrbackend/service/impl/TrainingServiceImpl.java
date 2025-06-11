package com.gump.hrbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gump.hrbackend.common.ErrorCode;
import com.gump.hrbackend.exception.BusinessException;
import com.gump.hrbackend.mapper.TrainingMapper;
import com.gump.hrbackend.model.entity.Employee;
import com.gump.hrbackend.model.entity.Training;
import com.gump.hrbackend.service.EmployeeService;
import com.gump.hrbackend.service.TrainingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 员工培训 业务逻辑实现类
 *
 * @author jzq
 */
@Service
public class TrainingServiceImpl extends ServiceImpl<TrainingMapper, Training>
        implements TrainingService {

    @Resource
    private TrainingMapper trainingMapper;

    @Resource
    private EmployeeService employeeService;

    /**
     * 新增员工
     *
     * @param training
     * @return
     */
    @Override
    public boolean addTraining(Training training) {
        Training trainingToAdd = new Training();
        trainingToAdd.setEmpName(training.getEmpName());
        trainingToAdd.setTrainingProject(training.getTrainingProject());
        trainingToAdd.setTrainingDuration(training.getTrainingDuration());
        trainingToAdd.setTrainer(training.getTrainer());
        // 校验员工姓名
        QueryWrapper<Employee> employeeQueryWrapper = new QueryWrapper<>();
        employeeQueryWrapper.eq("emp_name", training.getEmpName());
        Employee employee = employeeService.getOne(employeeQueryWrapper);
        if (employee == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "员工不存在");
        }
        trainingToAdd.setEmpId(employee.getId());
        boolean saveResult = this.save(trainingToAdd);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "创建培训记录失败，数据库错误");
        }
        return true;
    }

    /**
     * 更新员工
     *
     * @param training
     * @return
     */
    @Override
    public boolean updateTraining(Training training) {
        Training trainingToUpdate = new Training();
        trainingToUpdate.setId(training.getId());
        if (training.getTrainingDuration() != null) {
            trainingToUpdate.setTrainingDuration(training.getTrainingDuration());
        }
        if (StringUtils.isNotBlank(training.getEmpName())) {
            trainingToUpdate.setEmpName(training.getEmpName());
        }
        if (StringUtils.isNotBlank(training.getTrainingProject())) {
            trainingToUpdate.setTrainingProject(training.getTrainingProject());
        }
        if (StringUtils.isNotBlank(training.getTrainer())) {
            trainingToUpdate.setTrainer(training.getTrainer());
        }
        if (training.getTrainingDate() != null) {
            trainingToUpdate.setTrainingDate(training.getTrainingDate());
        }
        return trainingMapper.updateById(trainingToUpdate) > 0;
    }
}




