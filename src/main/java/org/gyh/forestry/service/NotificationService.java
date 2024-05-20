package org.gyh.forestry.service;

import org.gyh.forestry.domain.Notifications;
import org.gyh.forestry.domain.User;
import org.gyh.forestry.domain.UserNotification;
import org.gyh.forestry.mapper.NotificationsMapper;
import org.gyh.forestry.mapper.UserNotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * create by GYH on 2024/5/20
 */
@Service
public class NotificationService {
    @Autowired
    private NotificationsMapper notificationMapper;

    @Autowired
    private UserNotificationMapper userNotificationMapper;

    public void createNotification(Notifications notification, List<Integer> userIds) {
        notificationMapper.insertSelective(notification);
        for (int userId : userIds) {
            userNotificationMapper.insertUserNotification(userId, notification.getId());
        }
    }

    /**
     * 获取未读通知消息
     */
    public List<UserNotification> getUnreadNotifications() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = user.getId();
        return userNotificationMapper.getUnreadNotifications(userId);
    }

    /**
     * 获取未读消息数
     */
    public int getUnreadNotificationCount() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = user.getId();
        return userNotificationMapper.getUnreadNotificationCount(userId);
    }

    /**
     * 标记消息为已读
     */
    public void markAsRead(int userNotificationId) {
        userNotificationMapper.markAsRead(userNotificationId);
    }
}
