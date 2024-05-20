package org.gyh.forestry.service;

import org.gyh.forestry.domain.Notification;
import org.gyh.forestry.domain.UserNotification;
import org.gyh.forestry.mapper.NotificationMapper;
import org.gyh.forestry.mapper.UserNotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * create by GYH on 2024/5/20
 */
@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserNotificationMapper userNotificationMapper;

    public void createNotification(Notification notification, List<Integer> userIds) {
        notificationMapper.insertNotification(notification);
        for (int userId : userIds) {
            userNotificationMapper.insertUserNotification(userId, notification.getId());
        }
    }

    public List<UserNotification> getUnreadNotifications(int userId) {
        return userNotificationMapper.getUnreadNotifications(userId);
    }

    public int getUnreadNotificationCount(int userId) {
        return userNotificationMapper.getUnreadNotificationCount(userId);
    }

    public void markAsRead(int userNotificationId) {
        userNotificationMapper.markAsRead(userNotificationId);
    }
}
