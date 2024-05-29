package org.gyh.forestry.domain;

import lombok.Data;
import net.postgis.jdbc.geometry.Point;
import org.gyh.forestry.enums.PointTypeEnum;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

/**
 * create by GYH on 2024/5/29
 */
@Data
public class PointInfo {
    @Id
    private Integer id;

    /**
     * 点
     */
    private Point point;

    /**
     * 名字
     */
    private String name;

    /**
     * 类型（预警点，动物位置）
     */
    private PointTypeEnum type;

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
