package org.gyh.forestry.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * create by GYH on 2024/6/17
 * 动物管理
 */
@Data
public class UpdateAnimalManageReq {
    @NotNull
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

}