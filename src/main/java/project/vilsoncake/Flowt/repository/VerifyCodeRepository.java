package project.vilsoncake.Flowt.repository;

import org.springframework.data.repository.CrudRepository;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.entity.VerifyCodeEntity;

import java.util.Optional;

public interface VerifyCodeRepository extends CrudRepository<VerifyCodeEntity, Long> {
    Optional<VerifyCodeEntity> findByUser(UserEntity user);
    Optional<VerifyCodeEntity> findByCode(String code);
    Boolean existsByCode(String code);
    Boolean existsByUser(UserEntity user);
}
