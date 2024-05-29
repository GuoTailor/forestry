package org.gyh.forestry.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create by GYH on 2024/5/20
 */
@Data
public class NotificationsReq {

    /**
     * 标题
     */
    @Schema(description = "标题")
    @NotEmpty
    private String title;

    /**
     * 内容
     */
    @Schema(description = "内容")
    @NotEmpty
    private String content;

    /**
     * 发送时间
     */
    @Schema(description = "发送时间")
    @NotNull
    private LocalDateTime sendTime;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String creator;

    @Schema(description = "要发送的人，为空代表全部发送")
    private List<Integer> userIds;
}
