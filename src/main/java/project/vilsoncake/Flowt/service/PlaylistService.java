package project.vilsoncake.Flowt.service;

import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.dto.*;
import project.vilsoncake.Flowt.entity.PlaylistEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.exception.InvalidExtensionException;
import project.vilsoncake.Flowt.exception.MinioFileException;

import java.util.Map;

public interface PlaylistService {
    PlaylistDto getPlaylistByNameAndUser(String username, String playlistName);
    Map<String, String> createNewPlaylist(String authHeader, PlaylistRequest playListRequest);
    Map<String, String> addAvatarToPlaylist(String authHeader, String playListName, MultipartFile file) throws InvalidExtensionException;
    boolean removePlaylistAvatarByUserAndName(UserEntity user, String name);
    Map<String, String> addSongToPlaylist(String authHeader, String playlistName, String songAuthor, String songName);
    Map<String, String> addSongsToPlaylist(String authHeader, String playlistName, SongsListDto songsListDto);
    Map<String, String> removeSongFromPlaylist(String authHeader, String playlistName, String songAuthor, String songName);
    PlaylistEntity getPlaylistByUserAndName(UserEntity user, String name);
    PlaylistsPageDto getPublicPlaylistsBySubstring(String substring, int page, int size);
    byte[] getPlaylistAvatar(String username, String playlistName) throws MinioFileException;
    boolean removePlaylistByUserAndName(UserEntity user, String name);
    Map<String, String> removePlaylist(String authHeader, String playlistName);
}
