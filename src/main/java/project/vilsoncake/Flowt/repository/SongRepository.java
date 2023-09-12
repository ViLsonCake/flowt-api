package project.vilsoncake.Flowt.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import project.vilsoncake.Flowt.entity.SongEntity;
import project.vilsoncake.Flowt.entity.UserEntity;

import java.util.Optional;

public interface SongRepository extends CrudRepository<SongEntity, Long> {
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Optional<SongEntity> findByNameAndUser(String name, UserEntity user);
    Page<SongEntity> findAllByUser(UserEntity user, Pageable pageable);
    Boolean existsByNameAndUser(String name, UserEntity user);
}
