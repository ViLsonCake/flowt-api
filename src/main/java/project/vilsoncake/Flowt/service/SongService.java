package project.vilsoncake.Flowt.service;

import project.vilsoncake.Flowt.dto.SongRequest;
import project.vilsoncake.Flowt.dto.SongsResponse;
import project.vilsoncake.Flowt.dto.SubstringDto;
import project.vilsoncake.Flowt.entity.SongEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.entity.enumerated.Genre;
import project.vilsoncake.Flowt.exception.MinioFileException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SongService {
    Map<String, String> saveNewSongEntity(String authHeader, SongRequest songRequest);
    boolean removeSongAvatarByUserAndName(UserEntity user, String name);
    SongEntity getSongInfo(String username, String name);
    SongEntity getRandomSongInfoByGenre(String genre);
    SongEntity getRandomUserSong(UserEntity user);
    List<SongEntity> getRandomMostListenedSongsByGenres(List<Genre> genres);
    List<SongEntity> getMostPopularSongs(UserEntity user);
    Map<String, String> removeUserSong(String authHeader, String name);
    Map<String, String> updateListenerAndSongStatistic(String authHeader, String author, String name);
    boolean removeUserSongByUserAndName(UserEntity user, String name);
    SongsResponse getAuthenticatedUserSongs(String authHeader, int page, int size);
    SongsResponse getSongsByUsername(String username, int page, int size);
    SongsResponse getSongsByGenre(String genre, int page, int size);
    SongsResponse getSongsBySubstring(String substring, int page, int size);
    byte[] getSongAudioFile(String author, String name) throws MinioFileException;
    SongEntity findByNameAndUser(String name, UserEntity user);
    byte[] getSongAvatarBySongName(String username, String name) throws MinioFileException;
    void incrementSongListens(SongEntity song, UserEntity user);
}
