package org.gyh.forestry.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create by GYH on 2024/6/18
 */
@Data
public class AnimalManageResp {
    @Schema(description = "id")
    private Integer id;

    /**
     * 动物名称
     */
    @Schema(description = "动物名称")
    private String name;

    /**
     * 动物类型id
     */
    @Schema(description = "动物类型id")
    private Integer animalTypeId;

    /**
     * 动物描述
     */
    @Schema(description = "动物描述")
    private String describe;

    /**
     * 是否启用
     */
    @Schema(description = "是否启用")
    private Boolean enable;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
