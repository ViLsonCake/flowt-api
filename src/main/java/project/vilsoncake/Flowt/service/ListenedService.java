package project.vilsoncake.Flowt.service;

import project.vilsoncake.Flowt.entity.SongEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.entity.enumerated.Genre;

import java.util.List;

public interface ListenedService {
    int getUserListensCount(UserEntity user);
    boolean existsBySongs(SongEntity song);
    List<String> getMostListenedArtists(UserEntity user);
    List<Genre> getMostListenedGenres(UserEntity user);
}
