package org.gyh.forestry.dto.resp;

import lombok.Data;

/**
 * create by GYH on 2024/5/20
 */
@Data
public class UserNotificationResp {
    private Integer id;
    private Integer userId;
    private Integer notificationId;
    private Boolean isRead;
}
