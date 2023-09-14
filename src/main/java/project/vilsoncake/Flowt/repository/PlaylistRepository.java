package project.vilsoncake.Flowt.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import project.vilsoncake.Flowt.entity.PlaylistEntity;
import project.vilsoncake.Flowt.entity.UserEntity;

import java.util.Optional;

public interface PlaylistRepository extends CrudRepository<PlaylistEntity, Long> {
    Optional<PlaylistEntity> findByUserAndName(UserEntity user, String name);
    Boolean existsByUserAndName(UserEntity user, String name);
    Page<PlaylistEntity> findByNameContainingIgnoreCase(String substring, Pageable pageable);
}
