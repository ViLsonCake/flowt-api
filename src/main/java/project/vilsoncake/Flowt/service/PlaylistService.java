package project.vilsoncake.Flowt.service;

import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.dto.PlaylistDto;
import project.vilsoncake.Flowt.entity.PlaylistEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.exception.InvalidExtensionException;
import project.vilsoncake.Flowt.exception.MinioFileException;

import java.util.Map;

public interface PlaylistService {
    Map<String, String> createNewPlaylist(String authHeader, PlaylistDto playListDto);
    Map<String, String> addAvatarToPlaylist(String authHeader, String playListName, MultipartFile file) throws InvalidExtensionException;
    Map<String, String> addSongToPlaylist(String authHeader, String playlistName, String songAuthor, String songName);
    Map<String, String> removeSongFromPlaylist(String authHeader, String playlistName, String songAuthor, String songName);
    PlaylistEntity getPlaylistByUserAndName(UserEntity user, String name);
    byte[] getPlaylistAvatar(String username, String playlistName) throws MinioFileException;
}
