package project.vilsoncake.Flowt.service;

import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.dto.SongRequest;
import project.vilsoncake.Flowt.dto.SongsResponse;
import project.vilsoncake.Flowt.dto.SubstringDto;
import project.vilsoncake.Flowt.entity.SongEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.exception.InvalidExtensionException;
import project.vilsoncake.Flowt.exception.MinioFileException;

import java.util.Map;

public interface SongService {
    Map<String, String> saveNewSongEntity(String authHeader, SongRequest songRequest);
    Map<String, String> addAvatarByUserSongName(String authHeader, String name, MultipartFile file) throws InvalidExtensionException;
    Map<String, String> saveNewAudioFile(String authHeader, String name, MultipartFile file) throws InvalidExtensionException, MinioFileException;
    SongEntity getSongInfo(String username, String name);
    SongEntity getRandomSongInfoByGenre(String genre);
    Map<String, String> removeUserSong(String authHeader, String name);
    SongsResponse getSongsByUser(String authHeader, int page, int size);
    SongsResponse getSongsByGenre(String genre, int page, int size);
    SongsResponse getSongsBySubstring(SubstringDto substring, int page, int size);
    byte[] getSongAudioFile(String username, String name) throws MinioFileException;
    SongEntity findByNameAndUser(String name, UserEntity user);
    byte[] getSongAvatarBySongName(String username, String name) throws MinioFileException;
    void incrementSongListens(SongEntity song, UserEntity user);
}
