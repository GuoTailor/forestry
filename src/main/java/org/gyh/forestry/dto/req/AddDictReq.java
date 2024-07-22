package org.gyh.forestry.dto.req;

import lombok.Data;
import org.gyh.forestry.enums.DictTypeEnum;

/**
 * create by GYH on 2024/7/22
 */
@Data
public class AddDictReq {
    private String key;

    /**
     * 数据类型
     */
    private DictTypeEnum type;

    private String value;

    /**
     * 描述
     */
    private String describe;
}
