package project.vilsoncake.Flowt.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import project.vilsoncake.Flowt.entity.ListenedEntity;
import project.vilsoncake.Flowt.entity.UserListenedStatisticEntity;
import project.vilsoncake.Flowt.entity.enumerated.Genre;

import java.util.List;

public interface ListenedRepository extends CrudRepository<ListenedEntity, Long> {

    @Query(value = "SELECT genre " +
            "FROM listened WHERE user_listened_statistics_id = :statisticId " +
            "GROUP BY genre " +
            "ORDER BY count(genre) " +
            "DESC LIMIT 5", nativeQuery = true)
    List<Genre> getMostListenedGenres(Long statisticId);
    @Query(value = "SELECT author " +
            "FROM listened WHERE user_listened_statistics_id = :statisticId " +
            "GROUP BY author " +
            "ORDER BY count(author) " +
            "DESC LIMIT 5", nativeQuery = true)
    List<String> getMostListenedArtists(Long statisticId);
    @Query(value = "SELECT COUNT(*) FROM listened l WHERE l.user_listened_statistics_id = :userStatistic.getId()", nativeQuery = true)
    Integer getListensCount(UserListenedStatisticEntity userStatistic);
    Boolean existsByAuthorAndSongName(String author, String songName);
}
