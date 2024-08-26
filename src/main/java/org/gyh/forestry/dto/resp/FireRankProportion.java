package org.gyh.forestry.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * create by GYH on 2024/8/26
 */
@Data
public class FireRankProportion {
    @Schema(description = "火险等级")
    private Short level;
    @Schema(description = "数量")
    private Integer count;
    @Schema(description = "占比")
    private BigDecimal proportion;
}
