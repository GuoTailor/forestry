package org.gyh.forestry.controlle;

import org.gyh.forestry.domain.Notification;
import org.gyh.forestry.domain.UserNotification;
import org.gyh.forestry.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * create by GYH on 2024/5/20
 */
@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping("/create")
    public ResponseEntity<String> createNotification(@RequestBody Notification notification, @RequestParam List<Integer> userIds) {
        notificationService.createNotification(notification, userIds);
        return ResponseEntity.ok("Notification created successfully.");
    }

    @GetMapping("/unread/{userId}")
    public ResponseEntity<List<UserNotification>> getUnreadNotifications(@PathVariable int userId) {
        List<UserNotification> unreadNotifications = notificationService.getUnreadNotifications(userId);
        return ResponseEntity.ok(unreadNotifications);
    }

    @GetMapping("/unread/count/{userId}")
    public ResponseEntity<Integer> getUnreadNotificationCount(@PathVariable int userId) {
        int count = notificationService.getUnreadNotificationCount(userId);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/read/{id}")
    public ResponseEntity<String> markAsRead(@PathVariable int id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok("Notification marked as read.");
    }
}
