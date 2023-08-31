package project.vilsoncake.Flowt.repository;

import org.springframework.data.repository.CrudRepository;
import project.vilsoncake.Flowt.entity.UserAvatarEntity;
import project.vilsoncake.Flowt.entity.UserEntity;

public interface UserAvatarRepository extends CrudRepository<UserAvatarEntity, Long> {
    UserAvatarEntity findByFilename(String filename);
    UserAvatarEntity findByUser(UserEntity user);
    Boolean existsByFilename(String filename);
    Boolean existsByUser(UserEntity user);
}
