package org.gyh.forestry.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.gyh.forestry.dto.PageReq;

/**
 * create by GYH on 2024/6/4
 */
@Data
public class AnimalPageReq extends PageReq {
    @Schema(description = "名字")
    private String name;
    @Schema(description = "类型")
    private String type;
}
