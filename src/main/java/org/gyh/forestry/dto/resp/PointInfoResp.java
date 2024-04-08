package org.gyh.forestry.dto.resp;

import lombok.Data;

/**
 * create by GYH on 2024/4/7
 */
@Data
public class PointInfoResp {
    private Integer id;

    /**
     * 点
     */
    private JsonPoint point;

    /**
     * 名字
     */
    private String name;

    /**
     * 类型（预警点，动物位置）
     */
    private String type;
}
