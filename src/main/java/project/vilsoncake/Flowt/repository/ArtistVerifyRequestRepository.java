package project.vilsoncake.Flowt.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import project.vilsoncake.Flowt.entity.ArtistVerifyRequestEntity;
import project.vilsoncake.Flowt.entity.UserEntity;

import java.util.Optional;

public interface ArtistVerifyRequestRepository extends CrudRepository<ArtistVerifyRequestEntity, Long> {
    Optional<ArtistVerifyRequestEntity> findByUser(UserEntity user);
    Boolean existsByUser(UserEntity user);
    Page<ArtistVerifyRequestEntity> findAllByCheckedFalseOrderByCreatedAt(Pageable pageable);
}
