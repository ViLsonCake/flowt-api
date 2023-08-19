package project.vilsoncake.Flowt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.entity.VerifyCodeEntity;

import java.util.Optional;

@Repository
public interface VerifyCodeRepository extends JpaRepository<VerifyCodeEntity, Long> {
    Optional<VerifyCodeEntity> findByUser(UserEntity user);
}
