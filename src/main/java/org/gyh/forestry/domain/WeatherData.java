package org.gyh.forestry.domain;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * create by GYH on 2024/11/21
 */
@Data
public class WeatherData {
    private Integer id;

    private String name;

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

    private LocalDateTime updateTime;
}
