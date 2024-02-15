package project.vilsoncake.Flowt.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import project.vilsoncake.Flowt.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsernameIgnoreCase(String username);
    Optional<UserEntity> findByEmail(String email);
    Boolean existsUserByUsername(String username);
    Boolean existsUserByEmail(String email);
    Page<UserEntity> findByUsernameContainingIgnoreCaseOrderByFollowers(String substring, Pageable pageable);
}
