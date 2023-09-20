package project.vilsoncake.Flowt.repository;

import org.springframework.data.repository.CrudRepository;
import project.vilsoncake.Flowt.entity.NotificationEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.entity.enumerated.NotificationType;

import java.util.List;

public interface NotificationRepository extends CrudRepository<NotificationEntity, Long> {
    List<NotificationEntity> findAllByUser(UserEntity user);
    void deleteByType(NotificationType type);
}
