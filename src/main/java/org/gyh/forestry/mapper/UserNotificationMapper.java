package org.gyh.forestry.mapper;

import org.apache.ibatis.annotations.Param;
import org.gyh.forestry.domain.UserNotification;
import org.gyh.forestry.dto.resp.UserNotificationResp;

import java.util.List;

/**
 * create by GYH on 2024/5/20
 */
public interface UserNotificationMapper {

    UserNotification selectByPrimaryKey(int id);

    void insertUserNotification(@Param("userId") int userId, @Param("notificationId") int notificationId);

    List<UserNotificationResp> getUnreadNotifications(int userId);

    int getUnreadNotificationCount(int userId);

    void markAsRead(int id);
}
