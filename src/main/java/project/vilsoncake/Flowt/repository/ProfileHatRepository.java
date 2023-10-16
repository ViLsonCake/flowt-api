package project.vilsoncake.Flowt.repository;

import org.springframework.data.repository.CrudRepository;
import project.vilsoncake.Flowt.entity.ProfileHatEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.service.ProfileHatService;

public interface ProfileHatRepository extends CrudRepository<ProfileHatEntity, Long> {
    boolean existsByUser(UserEntity user);
    ProfileHatEntity getByUser(UserEntity user);
}
