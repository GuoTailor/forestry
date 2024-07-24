package org.gyh.forestry.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * create by GYH on 2024/7/24
 */
@Data
public class StatisticAnimalByMonthResp {
    @Schema(description = "年份")
    private String year;
    @Schema(description = "月份")
    private String month;
    @Schema(description = "数量")
    private Long count;
}
