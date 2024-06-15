package org.gyh.forestry.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * create by GYH on 2024/6/15
 */
@Data
public class UpdateAnimalTypeReq {
    @NotNull
    private Integer id;
    @Schema(description = "动物类型名字")
    private String name;
    @Schema(description = "是否启用")
    private Boolean enable;
}
