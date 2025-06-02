package com.gump.hrbackend.model.dto.position;

import lombok.Data;

import java.io.Serializable;

/**
 * 级别更新请求
 *
 * @author jzq
 */
@Data
public class PositionUpdateRequest implements Serializable {
    /**
     * 级别 id
     */
    private Long id;

    /**
     * 级别名称
     */
    private String title;

    /**
     * 级别职责
     */
    private String duty;

    private static final long serialVersionUID = 1L;
}