package project.vilsoncake.Flowt.service;

import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.dto.SongDto;
import project.vilsoncake.Flowt.exception.InvalidExtensionException;

import java.util.Map;

public interface SongService {
    Map<String, String> saveNewSongEntity(String authHeader, SongDto songDto);
    Map<String, String> addAvatarByUserSongName(String authHeader, String name, MultipartFile file) throws InvalidExtensionException;
    Map<String, String> saveNewAudioFile(String authHeader, String name, MultipartFile file);
}
