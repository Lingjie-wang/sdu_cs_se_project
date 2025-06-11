package com.gump.hrbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gump.hrbackend.model.entity.Training;

/**
 * 员工培训 业务逻辑接口
 *
 * @author jzq
 */
public interface TrainingService extends IService<Training> {
    /**
     * 新增员工培训
     *
     * @param training
     * @return
     */
    boolean addTraining(Training training);

    /**
     * 更新员工培训
     *
     * @param training
     * @return
     */
    boolean updateTraining(Training training);
}
