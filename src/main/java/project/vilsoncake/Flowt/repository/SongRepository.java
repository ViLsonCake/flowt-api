package project.vilsoncake.Flowt.repository;

import org.springframework.data.repository.CrudRepository;
import project.vilsoncake.Flowt.entity.SongEntity;
import project.vilsoncake.Flowt.entity.UserEntity;

import java.util.Optional;

public interface SongRepository extends CrudRepository<SongEntity, Long> {
    Optional<SongEntity> findByNameAndUser(String name, UserEntity user);
    Boolean existsByNameAndUser(String name, UserEntity user);
}
