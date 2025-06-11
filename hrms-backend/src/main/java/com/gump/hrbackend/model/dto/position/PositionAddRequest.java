package com.gump.hrbackend.model.dto.position;

import lombok.Data;

import java.io.Serializable;

/**
 * 级别创建请求
 *
 * @author jzq
 */
@Data
public class PositionAddRequest implements Serializable {

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