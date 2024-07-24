package org.gyh.forestry.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * create by GYH on 2024/7/24
 */
@Data
public class StatisticAnimalByCount {
    @Schema(description = "动物名称")
    private String name;
    @Schema(description = "动物数量")
    private Long count;
}
