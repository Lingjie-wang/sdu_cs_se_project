package com.gump.hrbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gump.hrbackend.common.BaseResponse;
import com.gump.hrbackend.common.DeleteRequest;
import com.gump.hrbackend.common.ErrorCode;
import com.gump.hrbackend.common.ResultUtils;
import com.gump.hrbackend.exception.BusinessException;
import com.gump.hrbackend.model.dto.training.TrainingAddRequest;
import com.gump.hrbackend.model.dto.training.TrainingQueryRequest;
import com.gump.hrbackend.model.dto.training.TrainingUpdateRequest;
import com.gump.hrbackend.model.entity.Training;
import com.gump.hrbackend.model.entity.User;
import com.gump.hrbackend.service.TrainingService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.gump.hrbackend.contant.UserConstant.ADMIN_ROLE;
import static com.gump.hrbackend.contant.UserConstant.USER_LOGIN_STATE;

/**
 * 员工培训接口
 *
 * @author jzq
 */
@RestController
@RequestMapping("/training")
public class TrainingController {
    @Resource
    private TrainingService trainingService;

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
     * @param trainingAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addTraining(@RequestBody TrainingAddRequest trainingAddRequest,
                                          HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (trainingAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Training training = new Training();
        BeanUtils.copyProperties(trainingAddRequest, training);
        boolean result = trainingService.addTraining(training);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(training.getId());
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteTraining(@RequestBody DeleteRequest deleteRequest,
                                                HttpServletRequest request) {
        // 仅管理员可删除
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = trainingService.removeById(deleteRequest.getId());
        return ResultUtils.success(result);
    }

    /**
     * 更新
     *
     * @param trainingUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateTraining(@RequestBody TrainingUpdateRequest trainingUpdateRequest,
                                                HttpServletRequest request) {
        // 仅管理员可更新
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (trainingUpdateRequest == null || trainingUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Training training = new Training();
        BeanUtils.copyProperties(trainingUpdateRequest, training);
        boolean result = trainingService.updateTraining(training);
        return ResultUtils.success(result);
    }

    /**
     * 获取列表
     *
     * @param trainingQueryRequest
     * @return
     */
    @GetMapping("/list")
    public BaseResponse<List<Training>> getTrainingList(TrainingQueryRequest trainingQueryRequest) {
        Training trainingQuery = new Training();
        if (trainingQueryRequest != null) {
            BeanUtils.copyProperties(trainingQueryRequest, trainingQuery);
        }
        QueryWrapper<Training> trainingQueryWrapper = new QueryWrapper<>(trainingQuery);
        if (trainingQuery.getEmpId() != null) {
            trainingQueryWrapper.eq("emp_id", trainingQuery.getEmpId()); // 根据员工 id 查询员工培训表
        }
        List<Training> trainingList = trainingService.list(trainingQueryWrapper);
        return ResultUtils.success(trainingList);
    }

}
