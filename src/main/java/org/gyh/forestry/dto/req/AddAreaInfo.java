package org.gyh.forestry.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * create by GYH on 2024/8/13
 */
@Data
public class AddAreaInfo {
    /**
     * 区域名字
     */
    @Schema(description = "区域名称")
    @NotEmpty
    private String name;

    /**
     * 面积
     */
    @Schema(description = "面积")
    private Float area;

    /**
     * 含水率
     */
    @Schema(description = "含水率")
    private Float moistureContent;

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

    /**
     * 天气地址
     */
    @Schema(description = "天气地址")
    private String weatherAddress;
}
