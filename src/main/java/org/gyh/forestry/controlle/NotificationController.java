package org.gyh.forestry.controlle;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gyh.forestry.domain.Notifications;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.ResponseInfo;
import org.gyh.forestry.dto.req.UnreadNotificationReq;
import org.gyh.forestry.dto.resp.UserNotificationResp;
import org.gyh.forestry.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/user")
    @Operation(summary = "获取当前用户的消息列表", security = {@SecurityRequirement(name = "Authorization")})
    public PageInfo<UserNotificationResp> getNotifications(@RequestBody UnreadNotificationReq pageReq) {
        return notificationService.getNotifications(pageReq);
    }

    @PostMapping("/all")
    @Operation(summary = "获取当前用户的消息列表", security = {@SecurityRequirement(name = "Authorization")})
    public PageInfo<Notifications> getAll(@RequestBody UnreadNotificationReq pageReq) {
        return notificationService.getAllNotifications(pageReq);
    }

    @GetMapping("/unread/count/{userId}")
    @Operation(summary = "获取当前用户的未读消息数", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<Integer> getUnreadNotificationCount() {
        int count = notificationService.getUnreadNotificationCount();
        return ResponseInfo.ok(count);
    }

    @PostMapping("/read/{id}")
    @Operation(summary = "读取消息并标记为已读", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<Notifications> markAsRead(@PathVariable int id) {
        return ResponseInfo.ok(notificationService.markAsRead(id));
    }
}
