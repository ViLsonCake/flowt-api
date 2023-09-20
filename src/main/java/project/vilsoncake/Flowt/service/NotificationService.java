package project.vilsoncake.Flowt.service;

import project.vilsoncake.Flowt.entity.NotificationEntity;
import project.vilsoncake.Flowt.entity.enumerated.NotificationType;
import project.vilsoncake.Flowt.entity.UserEntity;

import java.util.List;
import java.util.Map;

public interface NotificationService {
    boolean addNotification(NotificationType type, String message, UserEntity user);
    List<NotificationEntity> getNotificationsByUser(UserEntity user);
    Map<String, String> removeNotificationById(Long id);
    boolean removeNotificationByType(NotificationType type);
}
