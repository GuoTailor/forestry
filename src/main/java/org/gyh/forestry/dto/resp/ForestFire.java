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
    private Float temperature;
    private Short level;
}
