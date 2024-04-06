package org.gyh.forestry.domain;

import lombok.Data;
import net.postgis.jdbc.geometry.Point;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

/**
 * create by GYH on 2024/3/28
 */
@Data
public class Animal {
    @Id
    private Integer id;

    /**
     * 位置
     */
    private String locationZh;

    /**
     * 经纬度
     */
    private Point location;

    /**
     * 类型
     */
    private String type;

    /**
     * 时间
     */
    private LocalDateTime createTime;

    /**
     * 相机id
     */
    private String cameraId;

    /**
     * 动物名字
     */
    private String name;
}
