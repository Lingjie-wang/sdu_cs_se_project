package com.gump.hrbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gump.hrbackend.model.entity.Position;

/**
 * 级别 业务逻辑接口
 *
 * @author jzq
 */
public interface PositionService extends IService<Position> {
    /**
     * 新增级别
     *
     * @param position
     * @return
     */
    boolean addPosition(Position position);

    /**
     * 更新级别
     *
     * @param position
     * @return
     */
    boolean updatePosition(Position position);
}
