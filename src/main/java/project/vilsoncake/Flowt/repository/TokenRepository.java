package project.vilsoncake.Flowt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.vilsoncake.Flowt.entity.TokenEntity;
import project.vilsoncake.Flowt.entity.UserEntity;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    TokenEntity findByUser(UserEntity user);
    TokenEntity findByToken(String token);
    Boolean existsByToken(String token);
}
