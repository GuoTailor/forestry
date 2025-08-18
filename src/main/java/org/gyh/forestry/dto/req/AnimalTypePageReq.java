package org.gyh.forestry.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.gyh.forestry.dto.PageReq;

import java.time.LocalDateTime;

/**
 * create by GYH on 2024/6/15
 */
@Data
public class AnimalTypePageReq extends PageReq {
    private String name;
    @Schema(description = "创建时间-开始时间")
    private LocalDateTime startTime;
    @Schema(description = "创建时间-结束时间")
    private LocalDateTime endTime;
}
