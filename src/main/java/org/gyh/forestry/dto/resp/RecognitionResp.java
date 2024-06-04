package org.gyh.forestry.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * create by GYH on 2024/6/4
 */
@Data
public class RecognitionResp {
    @Schema(description = "动物名称")
    private String name;
    @Schema(description = "动物类型")
    private String type;
    @Schema(description = "动物图片")
    private String pic;
    @Schema(description = "动物描述")
    private String details;
}
