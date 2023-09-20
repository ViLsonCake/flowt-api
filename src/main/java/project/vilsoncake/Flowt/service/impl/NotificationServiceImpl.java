package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.vilsoncake.Flowt.entity.NotificationEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.entity.enumerated.NotificationType;
import project.vilsoncake.Flowt.exception.RemoveNotificationException;
import project.vilsoncake.Flowt.exception.NotificationNotFoundException;
import project.vilsoncake.Flowt.repository.NotificationRepository;
import project.vilsoncake.Flowt.service.NotificationService;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public boolean addNotification(NotificationType type, String message, UserEntity user) {
        notificationRepository.save(new NotificationEntity(type, message, user));
        return true;
    }

    @Override
    public List<NotificationEntity> getNotificationsByUser(UserEntity user) {
        return notificationRepository.findAllByUser(user);
    }

    @Override
    public Map<String, String> removeNotificationById(Long id) {
        if(notificationRepository.findById(id).isEmpty()) throw new NotificationNotFoundException("Notification not found");

        NotificationEntity notification = notificationRepository.findById(id).get();

        if (notification.getType().equals(NotificationType.MANDATORY)) throw new RemoveNotificationException("You can't remove notification with type 'mandatory'");

        notificationRepository.delete(notification);
        return Map.of("message", "Notification deleted");
    }

    @Override
    public boolean removeNotificationByType(NotificationType type) {
        notificationRepository.deleteByType(type);
        return true;
    }
}
