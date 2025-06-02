package com.gump.hrbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gump.hrbackend.common.ErrorCode;
import com.gump.hrbackend.exception.BusinessException;
import com.gump.hrbackend.mapper.PositionMapper;
import com.gump.hrbackend.model.entity.Position;
import com.gump.hrbackend.service.PositionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 级别 业务逻辑实现类
 *
 * @author jzq
 */
@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position>
        implements PositionService {

    @Resource
    private PositionMapper positionMapper;

    /**
     * 新增级别
     *
     * @param position
     * @return
     */
    @Override
    public boolean addPosition(Position position) {
        // 校验
        QueryWrapper<Position> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", position.getTitle());
        long count = positionMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "级别名称重复");
        }
        // 插入
        Position positionToAdd = new Position();
        positionToAdd.setTitle(position.getTitle());
        positionToAdd.setDuty(position.getDuty());
        boolean saveResult = this.save(positionToAdd);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "创建级别失败，数据库错误");
        }
        return true;
    }

    /**
     * 更新级别
     *
     * @param position
     * @return
     */
    @Override
    public boolean updatePosition(Position position) {
        Position positionToUpdate = new Position();
        positionToUpdate.setId(position.getId());
        if (StringUtils.isNotBlank(position.getTitle())) {
            QueryWrapper<Position> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("title", position.getTitle());
            long count = positionMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "级别名称重复");
            }
            positionToUpdate.setTitle(position.getTitle());
        }
        if (StringUtils.isNotBlank(position.getDuty())) {
            positionToUpdate.setDuty(position.getDuty());
        }
        return positionMapper.updateById(positionToUpdate) > 0;
    }
}




