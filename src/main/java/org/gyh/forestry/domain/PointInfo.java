package org.gyh.forestry.domain;

import lombok.Data;
import net.postgis.jdbc.geometry.Point;
import org.springframework.data.annotation.Id;

/**
 * create by GYH on 2024/4/6
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
    private String type;
}
