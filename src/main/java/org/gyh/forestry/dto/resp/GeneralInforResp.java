package org.gyh.forestry.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * create by GYH on 2024/7/23
 */
@Data
@AllArgsConstructor
public class GeneralInforResp {
    @Schema(description = "点位数量")
    private long pointCount;
    @Schema(description = "动物数量")
    private long animalCount;
    @Schema(description = "动物种类数量")
    private long animalTypeCount;
}
