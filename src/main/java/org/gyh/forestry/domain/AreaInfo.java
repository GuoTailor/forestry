package org.gyh.forestry.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

/**
 * create by GYH on 2024/8/13
 */
@Data
public class AreaInfo {
    @Id
    private Integer id;

    /**
     * 区域名字
     */
    private String name;

    /**
     * 面积
     */
    private Float area;

    /**
     * 含水率
     */
    private Float moistureContent;

    /**
     * 温度
     */
    private Float temperature;

    /**
     * 湿度
     */
    private Float humidity;

    /**
     * 风速
     */
    private Float windSpeed;

    /**
     * 风向
     */
    private String windDirection;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
