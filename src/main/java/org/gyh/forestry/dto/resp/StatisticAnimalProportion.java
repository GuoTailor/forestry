package org.gyh.forestry.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * create by GYH on 2024/7/24
 */
@Data
public class StatisticAnimalProportion {
    @Schema(description = "动物名称")
    private String name;
    @Schema(description = "动物占比")
    private Double proportion;
}
