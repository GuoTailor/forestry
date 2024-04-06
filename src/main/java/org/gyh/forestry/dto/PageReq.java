package org.gyh.forestry.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * create by GYH on 2022/11/21
 */
@Data
public class PageReq {
    @Schema(description = "第几页")
    private Integer page = 1;
    @Schema(description = "每页大小")
    private Integer pageSize = 30;
}
