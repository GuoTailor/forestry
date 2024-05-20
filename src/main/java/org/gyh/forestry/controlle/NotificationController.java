package org.gyh.forestry.controlle;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gyh.forestry.domain.Notifications;
import org.gyh.forestry.domain.UserNotification;
import org.gyh.forestry.dto.ResponseInfo;
import org.gyh.forestry.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * create by GYH on 2024/5/20
 */
@Tag(name = "通知")
@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping("/create")
    @Operation(summary = "创建一个通知", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<String> createNotification(@RequestBody Notifications notification, @RequestParam List<Integer> userIds) {
        notificationService.createNotification(notification, userIds);
        return ResponseInfo.ok("Notification created successfully.");
    }

    @GetMapping("/unread/{userId}")
    @Operation(summary = "创建一个通知", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<List<UserNotification>> getUnreadNotifications(@PathVariable int userId) {
        List<UserNotification> unreadNotifications = notificationService.getUnreadNotifications(userId);
        return ResponseInfo.ok(unreadNotifications);
    }

    @GetMapping("/unread/count/{userId}")
    public ResponseInfo<Integer> getUnreadNotificationCount(@PathVariable int userId) {
        int count = notificationService.getUnreadNotificationCount(userId);
        return ResponseInfo.ok(count);
    }

    @PostMapping("/read/{id}")
    public ResponseInfo<String> markAsRead(@PathVariable int id) {
        notificationService.markAsRead(id);
        return ResponseInfo.ok("Notification marked as read.");
    }
}
