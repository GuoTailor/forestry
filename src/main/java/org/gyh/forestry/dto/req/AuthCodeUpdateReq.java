package org.gyh.forestry.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.gyh.forestry.enums.CodeStateEnum;

import java.time.LocalDateTime;

/**
 * create by GYH on 2024/5/18
 */
@Data
public class AuthCodeUpdateReq {
    @NotNull
    private Integer id;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 状态CodeStateEnum
     */
    private CodeStateEnum status;

}
