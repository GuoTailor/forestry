package org.gyh.forestry.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * create by GYH on 2024/8/14
 */
@Data
public class ForestFire {
    private String name;
    @Schema(description = "面积")
    private double area;
    @Schema(description = "含水率")
    private double moistureContent;
    /**
     * 温度
     */
    @Schema(description = "温度")
    private Float temperature;
    /**
     * 湿度
     */
    @Schema(description = "湿度")
    private Float humidity;

    /**
     * 风速
     */
    @Schema(description = "风速")
    private Float windSpeed;

    /**
     * 风向
     */
    @Schema(description = "风向")
    private String windDirection;
    @Schema(description = "火险等级")
    private Short level;
}
