package project.vilsoncake.Flowt.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import project.vilsoncake.Flowt.entity.FollowerEntity;

public interface FollowerRepository extends CrudRepository<FollowerEntity, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM follower f WHERE f.user_id = :userId AND f.follower_id = :followerId", nativeQuery = true)
    void deleteByUserAndFollowerId(Long userId, Long followerId);
}
