package project.vilsoncake.Flowt.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import project.vilsoncake.Flowt.entity.FollowerEntity;
import project.vilsoncake.Flowt.entity.UserEntity;

public interface FollowerRepository extends CrudRepository<FollowerEntity, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM follower f WHERE f.user_id = :userId AND f.follower_id = :followerId", nativeQuery = true)
    void deleteByUserAndFollowerId(Long userId, Long followerId);
    @Query(value = "SELECT * FROM follower f WHERE f.user_id = :userId", nativeQuery = true)
    Page<FollowerEntity> findAllByUserId(Long userId, Pageable pageable);
    @Query(value = "SELECT * FROM follower f WHERE f.follower_id = :followerId", nativeQuery = true)
    Page<FollowerEntity> findAllByFollowerId(Long followerId, Pageable pageable);
}
