package project.vilsoncake.Flowt.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import project.vilsoncake.Flowt.entity.SongEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.entity.enumerated.Genre;

import java.util.List;
import java.util.Optional;

public interface SongRepository extends CrudRepository<SongEntity, Long> {
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Optional<SongEntity> findByNameAndUser(String name, UserEntity user);
    Page<SongEntity> findAllByUser(UserEntity user, Pageable pageable);
    Page<SongEntity> findAllByGenre(Genre genre, Pageable pageable);
    List<SongEntity> findAllByGenre(Genre genre);
    Boolean existsByNameAndUser(String name, UserEntity user);
    Page<SongEntity> findByNameContainingIgnoreCaseOrderByListensDesc(String substring, Pageable pageable);
    @Query(value = "SELECT * FROM song WHERE user_id = :userId ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    SongEntity getRandomUserSong(Long userId);
    @Query(value = "SELECT * FROM song WHERE genre = :genre ORDER BY listens DESC LIMIT 15", nativeQuery = true)
    List<SongEntity> getMostListenedSongsByGenre(String genre);
    @Query(value = "SELECT * FROM song WHERE user_id = :user.getUserId() ORDER BY listens DESC LIMIT 10", nativeQuery = true)
    List<SongEntity> getMostListenedSongsByUser(UserEntity user);
}
