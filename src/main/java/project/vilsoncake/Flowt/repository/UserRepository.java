package project.vilsoncake.Flowt.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import project.vilsoncake.Flowt.entity.PlaylistEntity;
import project.vilsoncake.Flowt.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    Boolean existsUserByUsername(String username);
    Boolean existsUserByEmail(String email);
    Page<UserEntity> findByUsernameContaining(String substring, Pageable pageable);
}
