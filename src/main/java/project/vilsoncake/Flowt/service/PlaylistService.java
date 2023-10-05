package project.vilsoncake.Flowt.service;

import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.dto.PlaylistDto;
import project.vilsoncake.Flowt.dto.PlaylistNameDto;
import project.vilsoncake.Flowt.dto.PlaylistsPageDto;
import project.vilsoncake.Flowt.dto.SubstringDto;
import project.vilsoncake.Flowt.entity.PlaylistEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.exception.InvalidExtensionException;
import project.vilsoncake.Flowt.exception.MinioFileException;

import java.util.Map;

public interface PlaylistService {
    Map<String, String> createNewPlaylist(String authHeader, PlaylistDto playListDto);
    Map<String, String> addAvatarToPlaylist(String authHeader, String playListName, MultipartFile file) throws InvalidExtensionException;
    boolean removePlaylistAvatarByUserAndName(UserEntity user, String name);
    Map<String, String> addSongToPlaylist(String authHeader, String playlistName, String songAuthor, String songName);
    Map<String, String> changePlaylistName(String authHeader, PlaylistNameDto playlistNameDto);
    Map<String, Boolean> changePlaylistAccessModifier(String authHeader, String playlistName);
    Map<String, String> removeSongFromPlaylist(String authHeader, String playlistName, String songAuthor, String songName);
    PlaylistEntity getPlaylistByUserAndName(UserEntity user, String name);
    PlaylistsPageDto getPublicPlaylistsBySubstring(SubstringDto substring, int page, int size);
    byte[] getPlaylistAvatar(String username, String playlistName) throws MinioFileException;
    boolean removePlaylistByUserAndName(UserEntity user, String name);
}
