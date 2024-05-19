package org.gyh.forestry.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.gyh.forestry.enums.CodeStateEnum;

import java.time.LocalDateTime;

/**
 * create by GYH on 2024/5/18
 */
@Data
public class AddAuthCodeReq {
    @Schema(description = "授权码")
    @NotEmpty
    private String code;
    @Schema(description = "开始时间，如果永久有效就不传")
    private LocalDateTime startTime;
    @Schema(description = "结束时间，如果永久有效就不传")
    private LocalDateTime endTime;
    @Schema(description = "状态")
    @NotNull
    private CodeStateEnum status;
}
