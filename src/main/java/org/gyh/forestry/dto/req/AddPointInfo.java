package org.gyh.forestry.dto.req;

import lombok.Data;
import org.gyh.forestry.dto.resp.JsonPoint;

/**
 * create by GYH on 2024/4/7
 */
@Data
public class AddPointInfo {
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
