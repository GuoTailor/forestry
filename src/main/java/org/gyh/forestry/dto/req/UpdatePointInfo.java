package org.gyh.forestry.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.gyh.forestry.dto.resp.JsonPoint;

/**
 * create by GYH on 2024/4/7
 */
@Data
public class UpdatePointInfo {
    @NotNull
    private Integer id;
    /**
     * 点
     */
    private JsonPoint point;

    /**
     * 名字
     */
    private String name;
    /**
     * 描述
     */
    @Schema(description = "描述")
    private String describe;
    /**
     * 类型（预警点，动物位置）
     */
    private String type;
}
