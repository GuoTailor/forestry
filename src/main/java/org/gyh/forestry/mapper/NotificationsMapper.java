package org.gyh.forestry.mapper;

import org.gyh.forestry.domain.Notification;
import org.gyh.forestry.domain.Notifications;

import java.util.List;

/**
 * create by GYH on 2024/5/20
 */
public interface NotificationsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Notifications record);

    int insertSelective(Notifications record);

    Notifications selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Notifications record);

    int updateByPrimaryKey(Notifications record);

    List<Notification> getAllNotifications();
}
