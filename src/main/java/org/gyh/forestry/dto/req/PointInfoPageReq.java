package org.gyh.forestry.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.gyh.forestry.dto.PageReq;
import org.gyh.forestry.enums.PointTypeEnum;

import java.time.LocalDateTime;

/**
 * create by GYH on 2024/6/3
 */
@Data
public class PointInfoPageReq extends PageReq {
    @Schema(description = "点位名称")
    private String name;
    /**
     * 创建时间
     */
    @Schema(description = "开始时间")
    private LocalDateTime startTime;
    @Schema(description = "结束时间")
    private LocalDateTime endTime;
    @Schema(description = "类型")
    private PointTypeEnum type;
    private Integer scenicSpotId;

    public PointInfoPageReq(PointInfoAllReq req) {
        this.type = req.getType();
    }

    public PointInfoPageReq() {
    }
}
