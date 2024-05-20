package org.gyh.forestry.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.gyh.forestry.domain.Notification;

import java.util.List;

/**
 * create by GYH on 2024/5/20
 */
public interface  NotificationMapper {
    @Insert("INSERT INTO notifications(title, content) VALUES(#{title}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertNotification(Notification notification);

    @Select("SELECT * FROM notifications")
    List<Notification> getAllNotifications();
}
