package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.dto.PlaylistDto;
import project.vilsoncake.Flowt.properties.MinioProperties;
import project.vilsoncake.Flowt.dto.PlaylistRequest;
import project.vilsoncake.Flowt.dto.PlaylistsPageDto;
import project.vilsoncake.Flowt.dto.SubstringDto;
import project.vilsoncake.Flowt.entity.PlaylistAvatarEntity;
import project.vilsoncake.Flowt.entity.PlaylistEntity;
import project.vilsoncake.Flowt.entity.SongEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.exception.InvalidExtensionException;
import project.vilsoncake.Flowt.exception.MinioFileException;
import project.vilsoncake.Flowt.exception.PlaylistAlreadyExistException;
import project.vilsoncake.Flowt.exception.PlaylistNotFoundException;
import project.vilsoncake.Flowt.repository.PlaylistRepository;
import project.vilsoncake.Flowt.service.*;
import project.vilsoncake.Flowt.utils.AuthUtils;
import project.vilsoncake.Flowt.utils.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserService userService;
    private final SongService songService;
    private final AvatarService playlistAvatarService;
    private final AuthUtils authUtils;
    private final FileUtils fileUtils;
    private final MinioFileService minioFileService;
    private final MinioProperties minioProperties;

    @Override
    public PlaylistDto getPlaylistByNameAndUser(String username, String playlistName) {
        UserEntity user = userService.getUserByUsername(username);
        PlaylistEntity playlist = getPlaylistByUserAndName(user, playlistName);

        return PlaylistDto.fromPlaylistEntity(playlist);
    }

    @Override
    public Map<String, String> createNewPlaylist(String authHeader, PlaylistRequest playlistRequest) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);

        // Check if user have playlist with the same name
        if (playlistRepository.existsByUserAndName(user, playlistRequest.getName())) {
            throw new PlaylistAlreadyExistException("User already have playlist with same name");
        }

        // Create new playlist and save
        PlaylistEntity playlist = new PlaylistEntity(
                playlistRequest.getName(),
                playlistRequest.getIsPrivate(),
                user
        );
        playlistRepository.save(playlist);

        return Map.of("message", String.format("PlayList '%s' saved", playlistRequest.getName()));
    }

    @Transactional
    @Override
    public Map<String, String> addAvatarToPlaylist(String authHeader, String playlistName, MultipartFile file) throws InvalidExtensionException {
        if (file.getOriginalFilename() != null && !fileUtils.isValidAvatarExtension(file.getOriginalFilename())) {
            throw new InvalidExtensionException("Invalid file extension (must be png or jpg)");
        }

        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);
        PlaylistEntity playlist = getPlaylistByUserAndName(user, playlistName);
        playlist.getPlaylistAvatar().setSize(String.valueOf(file.getSize()));
        String filename = playlist.getPlaylistAvatar().getFilename();

        // Save file data in minio storage
        minioFileService.saveFile(minioProperties.getPlaylistAvatarBucket(), filename, file);

        return Map.of("name", playlistName);
    }

    @Override
    public boolean removePlaylistAvatarByUserAndName(UserEntity user, String name) {
        PlaylistEntity playlist = getPlaylistByUserAndName(user, name);
        return playlistAvatarService.deleteAvatar(playlist);
    }

    @Override
    public Map<String, String> addSongToPlaylist(String authHeader, String playlistName, String songAuthor, String songName) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);
        SongEntity song = songService.findByNameAndUser(songName, user);
        PlaylistEntity playlist = getPlaylistByUserAndName(user, playlistName);

        Map<String, String> response = new HashMap<>();

        if (!playlist.getSongs().contains(song)) {
            playlist.getSongs().add(song);
            playlistRepository.save(playlist);
            response.put("message", String.format("Song '%s' added to playlist '%s'", songName, playlistName));
        } else {
            response.put("message", String.format("Song '%s' already exist in playlist '%s'", songName, playlistName));
        }

        return response;
    }

    @Override
    public Map<String, String> removeSongFromPlaylist(String authHeader, String playlistName, String songAuthor, String songName) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);
        SongEntity song = songService.findByNameAndUser(songName, user);
        PlaylistEntity playlist = getPlaylistByUserAndName(user, playlistName);

        Map<String, String> response = new HashMap<>();

        if (playlist.getSongs().contains(song)) {
            List<SongEntity> songsWithoutDeleted = new ArrayList<>();
            // Create new playlist songs list without specified song
            playlist.getSongs().forEach(playlistSong -> {
                if (!playlistSong.getName().equals(songName) && !playlistSong.getUser().getUsername().equals(songAuthor)) {
                    songsWithoutDeleted.add(playlistSong);
                }
            });
            playlist.setSongs(songsWithoutDeleted);
            playlistRepository.save(playlist);
            response.put("message", String.format("Song '%s' removed from playlist '%s'", songName, playlistName));
        } else {
            response.put("message", String.format("Song '%s' not in playlist '%s'", songName, playlistName));
        }

        return response;
    }

    @Override
    public byte[] getPlaylistAvatar(String username, String playlistName) throws MinioFileException {
        UserEntity user = userService.getUserByUsername(username);
        // Get user playlist
        PlaylistEntity playlist = getPlaylistByUserAndName(user, playlistName);
        PlaylistAvatarEntity playlistAvatar = playlist.getPlaylistAvatar();

        if (playlistAvatar == null) {
            throw new MinioFileException("File not found");
        }

        return minioFileService.getFileContent(minioProperties.getPlaylistAvatarBucket(), playlistAvatar.getFilename());
    }

    @Override
    public PlaylistsPageDto getPublicPlaylistsBySubstring(SubstringDto substringDto, int page, int size) {
        Page<PlaylistEntity> playlists = playlistRepository.findByIsPrivateFalseAndNameContainingIgnoreCase(substringDto.getSubstring(), PageRequest.of(page, size));

        return new PlaylistsPageDto(
                playlists.getTotalPages(),
                playlists.getContent().stream().map(PlaylistRequest::fromPlaylist).toList()
        );
    }

    @Transactional
    @Override
    public boolean removePlaylistByUserAndName(UserEntity user, String name) {
        PlaylistEntity playlist = getPlaylistByUserAndName(user, name);
        playlistRepository.delete(playlist);
        return true;
    }

    @Override
    public PlaylistEntity getPlaylistByUserAndName(UserEntity user, String name) {
        return playlistRepository.findByUserAndName(user, name).orElseThrow(() ->
                new PlaylistNotFoundException("Playlist not found"));
    }
}
