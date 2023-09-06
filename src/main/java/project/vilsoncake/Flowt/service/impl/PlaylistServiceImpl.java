package project.vilsoncake.Flowt.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.config.MinioConfig;
import project.vilsoncake.Flowt.dto.PlaylistDto;
import project.vilsoncake.Flowt.entity.PlaylistAvatarEntity;
import project.vilsoncake.Flowt.entity.PlaylistEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.exception.InvalidExtensionException;
import project.vilsoncake.Flowt.exception.MinioFileException;
import project.vilsoncake.Flowt.exception.PlaylistAlreadyExistException;
import project.vilsoncake.Flowt.exception.PlaylistNotFoundException;
import project.vilsoncake.Flowt.repository.PlaylistRepository;
import project.vilsoncake.Flowt.service.AvatarService;
import project.vilsoncake.Flowt.service.MinioFileService;
import project.vilsoncake.Flowt.service.PlaylistService;
import project.vilsoncake.Flowt.service.UserService;
import project.vilsoncake.Flowt.utils.AuthUtils;
import project.vilsoncake.Flowt.utils.FileUtils;

import java.util.ArrayList;
import java.util.Map;

@Service
@Slf4j
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playListRepository;
    private final UserService userService;
    private final AvatarService avatarService;
    private final AuthUtils authUtils;
    private final FileUtils fileUtils;
    private final MinioFileService minioFileService;
    private final MinioConfig minioConfig;

    public PlaylistServiceImpl(PlaylistRepository playListRepository, UserService userService, @Qualifier("playlistAvatarServiceImpl") AvatarService avatarService, AuthUtils authUtils, FileUtils fileUtils, MinioFileService minioFileService, MinioConfig minioConfig) {
        this.playListRepository = playListRepository;
        this.userService = userService;
        this.avatarService = avatarService;
        this.authUtils = authUtils;
        this.fileUtils = fileUtils;
        this.minioFileService = minioFileService;
        this.minioConfig = minioConfig;
    }

    @Override
    public Map<String, String> createNewPlaylist(String authHeader, PlaylistDto playlistDto) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);

        // Check if user have playlist with the same name
        if (playListRepository.existsByUserAndName(user, playlistDto.getName()))
            throw new PlaylistAlreadyExistException("User already have playlist with same name");

        // Create new playlist and save
        PlaylistEntity playlist = new PlaylistEntity(
                playlistDto.getName(),
                new ArrayList<>(),
                user
        );
        playListRepository.save(playlist);

        return Map.of("message", String.format("PlayList '%s' saved", playlistDto.getName()));
    }

    @Override
    public Map<String, String> addAvatarToPlayList(String authHeader, String playlistName, MultipartFile file) throws InvalidExtensionException {

        if (!fileUtils.isValidExtension(file.getOriginalFilename())) {
            throw new InvalidExtensionException("Invalid file extension (must be png or jpg)");
        }

        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);
        // Get user playlist
        PlaylistEntity playlist = getPlaylistByUserAndName(user, playlistName);

        String filename;

        if (!avatarService.existsByEntity(playlist)) {
            // Generate filename
            filename = fileUtils.generateRandomUUID();
            // Save file info in sql
            avatarService.saveAvatar(file, filename, playlist);
        } else {
            filename = ((PlaylistAvatarEntity) avatarService.getByEntity(user)).getFilename();
        }

        // Save file data in minio storage
        minioFileService.saveFile(minioConfig.getPlaylistAvatarBucket(), filename, file);

        return Map.of("name", playlistName);
    }

    @Override
    public byte[] getPlaylistAvatar(String username, String playlistName) throws MinioFileException {
        UserEntity user = userService.getUserByUsername(username);
        // Get user playlist
        PlaylistEntity playlist = getPlaylistByUserAndName(user, playlistName);
        PlaylistAvatarEntity playlistAvatar = playlist.getPlaylistAvatar();

        if (playlistAvatar == null) throw new MinioFileException("File not found");

        return minioFileService.getFileContent(minioConfig.getPlaylistAvatarBucket(), playlistAvatar.getFilename());
    }

    @Override
    public PlaylistEntity getPlaylistByUserAndName(UserEntity user, String name) {
        return playListRepository.findByUserAndName(user, name).orElseThrow(() ->
                new PlaylistNotFoundException("Playlist not found"));
    }
}
