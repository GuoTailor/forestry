package org.gyh.forestry.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * create by GYH on 2024/10/19
 */
@Data
public class AreaAnimalResp {
    @Schema(description = "区域名称")
    private String areaName;
    private List<AnimalInfo> animalInfo;

    @Data
    public static class AnimalInfo {
        @Schema(description = "动物类型")
        private String animalType;
        @Schema(description = "数量")
        private Integer count;
        @Schema(description = "占比")
        private BigDecimal proportion;
    }
}
