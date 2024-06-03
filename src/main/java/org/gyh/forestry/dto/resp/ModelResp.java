package org.gyh.forestry.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create by GYH on 2024/5/28
 */
@Data
public class ModelResp {
    private Integer id;

    /**
     * 模型名字
     */
    @Schema(description = "模型名字")
    private String name;

    /**
     * 是否启用
     */
    @Schema(description = "是否启用")
    private Boolean enable;

    /**
     * 模型路径
     */
    @Schema(description = "模型路径")
    private String path;

    /**
     * 创建者
     */
    @Schema(description = "创建者")
    private String creator;

    /**
     * 上传时间
     */
    @Schema(description = "上传时间")
    private LocalDateTime createTime;
}
