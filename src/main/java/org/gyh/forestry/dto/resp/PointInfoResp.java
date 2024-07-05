package org.gyh.forestry.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.gyh.forestry.dto.JsonPoint;
import org.gyh.forestry.enums.PointTypeEnum;

import java.time.LocalDateTime;

/**
 * create by GYH on 2024/4/7
 */
@Data
public class PointInfoResp {
    private Integer id;

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
     * 类型（预警点，动物位置）
     */
    @Schema(description = "类型（预警点，动物位置）")
    private PointTypeEnum type;
    /**
     * 描述
     */
    @Schema(description = "描述")
    private String describe;

    /**
     * 设备id
     */
    @Schema(description = "设备id")
    private Integer deviceId;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String creator;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
