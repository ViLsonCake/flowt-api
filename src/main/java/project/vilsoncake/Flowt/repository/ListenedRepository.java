package project.vilsoncake.Flowt.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import project.vilsoncake.Flowt.entity.ListenedEntity;
import project.vilsoncake.Flowt.entity.UserListenedStatisticEntity;

import java.util.List;

public interface ListenedRepository extends CrudRepository<ListenedEntity, Long> {
    @Query(value = "SELECT l.genre, COUNT(l.genre) as listen_count FROM listened l " +
            "WHERE l.user_listened_statistics_id = :userStatistic.getId() " +
            "GROUP BY l.genre " +
            "ORDER BY listen_count " +
            "DESC LIMIT 5",
            nativeQuery = true)
    List<ListenedEntity> getMostListenedGenres(UserListenedStatisticEntity userStatistic);
    @Query(value = "SELECT l.author, COUNT(l.author) as listen_count FROM listened l " +
            "WHERE l.user_listened_statistics_id = :userStatistic.getId() " +
            "GROUP BY l.author " +
            "ORDER BY listen_count " +
            "DESC LIMIT 5",
            nativeQuery = true)
    List<ListenedEntity> getMostListenedArtists(UserListenedStatisticEntity userStatistic);
    @Query(value = "SELECT COUNT(*) FROM listened l WHERE l.user_listened_statistics_id = :userStatistic.getId()", nativeQuery = true)
    Integer getListensCount(UserListenedStatisticEntity userStatistic);
    Boolean existsByAuthorAndSongName(String author, String songName);
}
