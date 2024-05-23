package org.gyh.forestry.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.gyh.forestry.domain.Notifications;
import org.gyh.forestry.domain.OperationRecord;
import org.gyh.forestry.domain.User;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.req.UnreadNotificationReq;
import org.gyh.forestry.dto.resp.UserNotificationResp;
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
     * 获取用户获取通知消息
     */
    public PageInfo<UserNotificationResp> getNotifications(UnreadNotificationReq pageReq) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = user.getId();
        try (Page<OperationRecord> page = PageHelper.startPage(pageReq.getPage(), pageReq.getPageSize())) {
            List<UserNotificationResp> unreadNotifications = userNotificationMapper.getUnreadNotifications(userId);
            return PageInfo.ok(page.getTotal(), pageReq, unreadNotifications);
        }
    }

    /**
     * 获取所有通知消息
     */
    public PageInfo<Notifications> getAllNotifications(UnreadNotificationReq pageReq) {
        try (Page<OperationRecord> page = PageHelper.startPage(pageReq.getPage(), pageReq.getPageSize())) {
            List<Notifications> notifications = notificationMapper.getAllNotifications();
            return PageInfo.ok(page.getTotal(), pageReq, notifications);
        }
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
