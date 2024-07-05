package org.gyh.forestry.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.gyh.forestry.enums.PointTypeEnum;

/**
 * create by GYH on 2024/7/5
 */
@Data
public class PointInfoAllReq {

    @Schema(description = "类型")
    private PointTypeEnum type;
}
