package org.gyh.forestry.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

/**
 * create by GYH on 2024/5/30
 */
@Data
public class UserNotifications {
    @Id
    private Integer id;

    private Integer userId;

    private Integer notificationId;

    private Boolean isRead;

    /**
     * 发送时间
     */
    private LocalDateTime sendTime;
}