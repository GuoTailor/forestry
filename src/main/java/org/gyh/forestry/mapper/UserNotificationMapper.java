package org.gyh.forestry.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.gyh.forestry.domain.UserNotification;

import java.util.List;

/**
 * create by GYH on 2024/5/20
 */
public interface UserNotificationMapper {
    @Insert("INSERT INTO user_notifications(user_id, notification_id) VALUES(#{userId}, #{notificationId})")
    void insertUserNotification(@Param("userId") int userId, @Param("notificationId") int notificationId);

    @Select("SELECT * FROM user_notifications WHERE user_id = #{userId} AND is_read = FALSE")
    List<UserNotification> getUnreadNotifications(int userId);

    @Select("SELECT COUNT(*) FROM user_notifications WHERE user_id = #{userId} AND is_read = FALSE")
    int getUnreadNotificationCount(int userId);

    @Update("UPDATE user_notifications SET is_read = TRUE WHERE id = #{id}")
    void markAsRead(int id);
}
