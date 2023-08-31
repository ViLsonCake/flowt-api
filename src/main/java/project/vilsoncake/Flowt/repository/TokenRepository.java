package project.vilsoncake.Flowt.repository;

import org.springframework.data.repository.CrudRepository;
import project.vilsoncake.Flowt.entity.TokenEntity;
import project.vilsoncake.Flowt.entity.UserEntity;

public interface TokenRepository extends CrudRepository<TokenEntity, Long> {
    TokenEntity findByUser(UserEntity user);
    TokenEntity findByToken(String token);
    Boolean existsByToken(String token);
}
