package org.gyh.forestry.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create by GYH on 2024/8/13
 */
@Data
public class AreaInfoResp {
    private Integer id;

    /**
     * 区域名字
     */
    @Schema(description = "区域名字")
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
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    /**
     * 天气地址
     */
    @Schema(description = "天气地址")
    private String weatherAddress;
}
