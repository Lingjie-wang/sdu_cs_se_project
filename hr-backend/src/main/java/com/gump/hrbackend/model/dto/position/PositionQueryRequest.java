package com.gump.hrbackend.model.dto.position;

import com.gump.hrbackend.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 *
 * @author jzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PositionQueryRequest extends PageRequest implements Serializable {

    /**
     * 级别名称
     */
    private String title;

    private static final long serialVersionUID = 1L;
}
