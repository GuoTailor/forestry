package org.gyh.forestry.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * create by GYH on 2024/5/27
 */
@Data
public class UpdateMenuRoleRep {
    @Schema(description = "角色id")
    @NotNull
    private Integer rid;
    @Schema(description = "菜单id")
    private List<Integer> mids;
}
