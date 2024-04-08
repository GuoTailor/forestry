package org.gyh.forestry.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.gyh.forestry.dto.PageReq;

import java.time.LocalDateTime;

/**
 * create by GYH on 2024/3/30
 */
@Data
public class OperationRecordPage extends PageReq {
    @Schema(description = "模型名字")
    private String modelName;
    /**
     * 操作用户
     */
    @Schema(description = "操作用户")
    private String operationUser;
    /**
     * 请求方式
     */
    @Schema(description = "请求方式")
    private String method;
    /**
     * 操作状态
     */
    @Schema(description = "操作状态")
    private Boolean state;
    /**
     * 操作时间
     */
    @Schema(description = "操作开始时间")
    private LocalDateTime startCreateTime;
    @Schema(description = "操作结束时间")
    private LocalDateTime endCreateTime;
}
