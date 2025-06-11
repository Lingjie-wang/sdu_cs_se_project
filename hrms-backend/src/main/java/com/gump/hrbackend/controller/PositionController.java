package com.gump.hrbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gump.hrbackend.common.BaseResponse;
import com.gump.hrbackend.common.DeleteRequest;
import com.gump.hrbackend.common.ErrorCode;
import com.gump.hrbackend.common.ResultUtils;
import com.gump.hrbackend.exception.BusinessException;
import com.gump.hrbackend.model.dto.position.PositionAddRequest;
import com.gump.hrbackend.model.dto.position.PositionQueryRequest;
import com.gump.hrbackend.model.dto.position.PositionUpdateRequest;
import com.gump.hrbackend.model.entity.Position;
import com.gump.hrbackend.model.entity.User;
import com.gump.hrbackend.service.PositionService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.gump.hrbackend.contant.UserConstant.ADMIN_ROLE;
import static com.gump.hrbackend.contant.UserConstant.USER_LOGIN_STATE;

/**
 * 级别接口
 *
 * @author jzq
 */
@RestController
@RequestMapping("/position")
public class PositionController {
    @Resource
    private PositionService positionService;

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
     * @param positionAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addPosition(@RequestBody PositionAddRequest positionAddRequest,
                                          HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (positionAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Position position = new Position();
        BeanUtils.copyProperties(positionAddRequest, position);
        boolean result = positionService.addPosition(position);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(position.getId());
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deletePosition(@RequestBody DeleteRequest deleteRequest,
                                                HttpServletRequest request) {
        // 仅管理员可删除
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = positionService.removeById(deleteRequest.getId());
        return ResultUtils.success(result);
    }

    /**
     * 更新
     *
     * @param positionUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updatePosition(@RequestBody PositionUpdateRequest positionUpdateRequest,
                                                HttpServletRequest request) {
        // 仅管理员可更新
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (positionUpdateRequest == null || positionUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Position position = new Position();
        BeanUtils.copyProperties(positionUpdateRequest, position);
        boolean result = positionService.updatePosition(position);
        return ResultUtils.success(result);
    }

    /**
     * 获取列表
     *
     * @param positionQueryRequest
     * @return
     */
    @GetMapping("/list")
    public BaseResponse<List<Position>> getPositionList(PositionQueryRequest positionQueryRequest) {
        Position positionQuery = new Position();
        if (positionQueryRequest != null) {
            BeanUtils.copyProperties(positionQueryRequest, positionQuery);
        }
        QueryWrapper<Position> queryWrapper = new QueryWrapper<>(positionQuery);
        List<Position> positionList = positionService.list(queryWrapper);
        return ResultUtils.success(positionList);
    }

}
