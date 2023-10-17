package project.vilsoncake.Flowt.repository;

import org.springframework.data.repository.CrudRepository;
import project.vilsoncake.Flowt.entity.ProfileHeaderEntity;
import project.vilsoncake.Flowt.entity.UserEntity;

public interface ProfileHeaderRepository extends CrudRepository<ProfileHeaderEntity, Long> {
    boolean existsByUser(UserEntity user);
    ProfileHeaderEntity getByUser(UserEntity user);
}
