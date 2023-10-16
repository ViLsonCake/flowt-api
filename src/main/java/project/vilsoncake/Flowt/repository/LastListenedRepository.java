package project.vilsoncake.Flowt.repository;

import org.springframework.data.repository.CrudRepository;
import project.vilsoncake.Flowt.entity.LastListenedEntity;
import project.vilsoncake.Flowt.entity.UserEntity;

public interface LastListenedRepository extends CrudRepository<LastListenedEntity, Long> {
    LastListenedEntity findByUser(UserEntity user);
}
