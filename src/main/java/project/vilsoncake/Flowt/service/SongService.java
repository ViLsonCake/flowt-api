package project.vilsoncake.Flowt.service;

import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.dto.SongDto;
import project.vilsoncake.Flowt.entity.SongEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.exception.InvalidExtensionException;
import project.vilsoncake.Flowt.exception.MinioFileException;

import java.util.Map;

public interface SongService {
    Map<String, String> saveNewSongEntity(String authHeader, SongDto songDto);
    Map<String, String> addAvatarByUserSongName(String authHeader, String name, MultipartFile file) throws InvalidExtensionException;
    Map<String, String> saveNewAudioFile(String authHeader, String name, MultipartFile file) throws InvalidExtensionException;
    Map<String, String> getSongInfo(String username, String name);
    byte[] getSongAudioFile(String username, String name) throws MinioFileException;
    SongEntity findByNameAndUser(String name, UserEntity user);
    byte[] getSongAvatarBySongName(String username, String name) throws MinioFileException;
}
