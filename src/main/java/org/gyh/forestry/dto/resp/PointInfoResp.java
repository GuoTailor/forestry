package org.gyh.forestry.dto.resp;

import lombok.Data;

import java.time.LocalDateTime;

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
    /**
     * 描述
     */
    private String describe;

    /**
     * 设备id
     */
    private Integer deviceId;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;
}
