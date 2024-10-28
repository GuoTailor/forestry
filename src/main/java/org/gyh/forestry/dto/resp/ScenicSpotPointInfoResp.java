package org.gyh.forestry.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create by GYH on 2024/4/7
 */
@Data
public class ScenicSpotPointInfoResp {
    private Integer id;

    @Schema(description = "景区名称")
    private String scenicSpotName;
    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String creator;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    private List<PointInfoResp> points;
}
