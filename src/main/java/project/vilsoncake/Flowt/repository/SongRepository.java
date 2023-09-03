package project.vilsoncake.Flowt.repository;

import org.springframework.data.repository.CrudRepository;
import project.vilsoncake.Flowt.entity.SongEntity;
import project.vilsoncake.Flowt.entity.UserEntity;

public interface SongRepository extends CrudRepository<SongEntity, Long> {
    SongEntity findByNameAndUser(String name, UserEntity user);
    Boolean existsByNameAndUser(String name, UserEntity user);
}
