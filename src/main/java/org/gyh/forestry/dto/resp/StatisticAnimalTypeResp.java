package org.gyh.forestry.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * create by GYH on 2024/7/23
 */
@Data
public class StatisticAnimalTypeResp {
    @Schema(description = "动物类型名称")
    private String name;
    @Schema(description = "动物类型数量")
    private Long count;
}
