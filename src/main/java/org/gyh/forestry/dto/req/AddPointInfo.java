package org.gyh.forestry.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.gyh.forestry.dto.JsonPoint;
import org.gyh.forestry.enums.PointTypeEnum;

/**
 * create by GYH on 2024/4/7
 */
@Data
public class AddPointInfo {
    /**
     * 点
     */
    @Schema(description = "点")
    private JsonPoint point;

    /**
     * 名字
     */
    @Schema(description = "名字")
    private String name;
    /**
     * 描述
     */
    @Schema(description = "描述")
    private String describe;
    /**
     * 类型（预警点，动物位置）
     */
    @Schema(description = "类型（预警点，动物位置）")
    private PointTypeEnum type;

    /**
     * 相机编号
     */
    @Schema(description = "相机编号")
    private String cameraNum;

    /**
     * 所属景区
     */
    @Schema(description = "景区id")
    private Integer scenicSpotId;
}
