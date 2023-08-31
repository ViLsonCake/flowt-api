package project.vilsoncake.Flowt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.vilsoncake.Flowt.entity.FollowerEntity;

@Repository
public interface FollowerRepository extends JpaRepository<FollowerEntity, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM follower f WHERE f.user_id = :userId AND f.follower_id = :followerId", nativeQuery = true)
    void deleteByUserAndFollowerId(Long userId, Long followerId);
}
