package project.vilsoncake.Flowt.service;

import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.dto.SongNameDto;
import project.vilsoncake.Flowt.entity.SongEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.exception.InvalidExtensionException;
import project.vilsoncake.Flowt.exception.MinioFileException;

import java.util.Map;

public interface SongChangeService {
    Map<String, String> addAvatarByUserSongName(String authHeader, String name, MultipartFile file) throws InvalidExtensionException;
    Map<String, String> saveNewAudioFile(String authHeader, String name, MultipartFile file) throws InvalidExtensionException, MinioFileException;
    Map<String, String> changeSongName(String authHeader, SongNameDto songNameDto);
    SongEntity findByNameAndUser(String name, UserEntity user);
}
