package org.gyh.forestry.domain;

import lombok.Data;
import org.gyh.forestry.enums.DictTypeEnum;
import org.springframework.data.annotation.Id;

/**
 * create by GYH on 2024/7/22
 */
@Data
public class Dict {
    @Id
    private Integer id;

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
