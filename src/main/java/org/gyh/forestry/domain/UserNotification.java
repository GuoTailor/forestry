package org.gyh.forestry.domain;

import lombok.Data;

/**
 * create by GYH on 2024/5/20
 */
@Data
public class UserNotification {
    private Integer id;
    private Integer userId;
    private Integer notificationId;
    private Boolean isRead;
}
