package org.gyh.forestry.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.gyh.forestry.dto.PageReq;

import java.time.LocalDateTime;

/**
 * create by GYH on 2024/5/28
 */
@Data
public class ModelPageReq extends PageReq {
    @Schema(description = "模型名字")
    private String name;
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;
    @Schema(description = "上传者")
    private String creator;
}
