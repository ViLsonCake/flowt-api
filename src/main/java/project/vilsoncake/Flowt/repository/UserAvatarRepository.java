package project.vilsoncake.Flowt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.vilsoncake.Flowt.entity.UserAvatarEntity;
import project.vilsoncake.Flowt.entity.UserEntity;

@Repository
public interface UserAvatarRepository extends JpaRepository<UserAvatarEntity, Long> {
    UserAvatarEntity findByFilename(String filename);
    UserAvatarEntity findByUser(UserEntity user);
    Boolean existsByFilename(String filename);
    Boolean existsByUser(UserEntity user);
}
