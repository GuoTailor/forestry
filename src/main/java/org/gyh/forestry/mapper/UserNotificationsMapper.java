package org.gyh.forestry.mapper;

import org.gyh.forestry.domain.UserNotifications;
import org.gyh.forestry.dto.resp.UserNotificationResp;

import java.util.List;

/**
 * create by GYH on 2024/5/30
 */
public interface UserNotificationsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserNotifications record);

    int insertSelective(UserNotifications record);

    UserNotifications selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserNotifications record);

    int updateByPrimaryKey(UserNotifications record);

    List<UserNotificationResp> getUnreadNotifications(int userId);

    int getUnreadNotificationCount(int userId);

    void markAsRead(int id);
}