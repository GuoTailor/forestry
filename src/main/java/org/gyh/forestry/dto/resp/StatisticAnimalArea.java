package org.gyh.forestry.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * create by GYH on 2024/7/23
 */
@Data
public class StatisticAnimalArea {
    @Schema(description = "动物类型名称")
    private String name;
    @Schema(description = "区域id")
    private Integer areaId;
    @Schema(description = "区域名称")
    private String typeName;
}
