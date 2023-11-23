package project.vilsoncake.Flowt.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import project.vilsoncake.Flowt.entity.TokenEntity;
import project.vilsoncake.Flowt.entity.UserEntity;

public interface TokenRepository extends CrudRepository<TokenEntity, Long> {
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    TokenEntity findByUser(UserEntity user);
    TokenEntity findByToken(String token);
    Boolean existsByToken(String token);
}
