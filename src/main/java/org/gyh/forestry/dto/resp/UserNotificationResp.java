package org.gyh.forestry.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * create by GYH on 2024/5/20
 */
@Data
public class UserNotificationResp {
    @Schema(description = "id")
    private Integer id;
    @Schema(description = "用户id")
    private Integer userId;
    @Schema(description = "通知id")
    private Integer notificationId;
    /**
     * 标题
     */
    @Schema(description = "标题")
    private String title;
    /**
     * 内容
     */
    @Schema(description = "内容")
    private String content;
    @Schema(description = "是否已读")
    private Boolean isRead;
}
