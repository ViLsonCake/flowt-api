package project.vilsoncake.Flowt.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.config.MinioConfig;
import project.vilsoncake.Flowt.dto.SongDto;
import project.vilsoncake.Flowt.entity.SongAvatarEntity;
import project.vilsoncake.Flowt.entity.SongEntity;
import project.vilsoncake.Flowt.entity.UserAvatarEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.exception.InvalidExtensionException;
import project.vilsoncake.Flowt.exception.MinioFileException;
import project.vilsoncake.Flowt.exception.SongAlreadyExistByUserException;
import project.vilsoncake.Flowt.exception.SongNotFoundException;
import project.vilsoncake.Flowt.repository.SongRepository;
import project.vilsoncake.Flowt.service.*;
import project.vilsoncake.Flowt.utils.AuthUtils;
import project.vilsoncake.Flowt.utils.FileUtils;

import java.util.Map;

@Service
@Slf4j
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final UserService userService;
    private final AuthUtils authUtils;
    private final FileUtils fileUtils;
    private final AvatarService avatarService;
    private final AudioFileService audioFileService;
    private final MinioFileService minioFileService;
    private final MinioConfig minioConfig;

    public SongServiceImpl(SongRepository songRepository, UserService userService, AuthUtils authUtils, FileUtils fileUtils, @Qualifier("songAvatarServiceImpl") AvatarService avatarService, AudioFileService audioFileService, MinioFileService minioFileService, MinioConfig minioConfig) {
        this.songRepository = songRepository;
        this.userService = userService;
        this.authUtils = authUtils;
        this.fileUtils = fileUtils;
        this.avatarService = avatarService;
        this.audioFileService = audioFileService;
        this.minioFileService = minioFileService;
        this.minioConfig = minioConfig;
    }

    @Override
    public Map<String, String> saveNewSongEntity(String authHeader, SongDto songDto) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);

        // If user have song with the same name
        if (songRepository.existsByNameAndUser(songDto.getName(), user)) throw new SongAlreadyExistByUserException("User have name with same name");

        // Save new song
        SongEntity song = new SongEntity();
        song.setName(songDto.getName());
        song.setIssueYear(songDto.getIssueYear());
        song.setGenre(songDto.getGenre());
        song.setUser(user);
        song.setListens(0L);
        songRepository.save(song);

        return Map.of("name", songDto.getName());
    }

    @Override
    public Map<String, String> saveNewAudioFile(String authHeader, String name, MultipartFile file) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);
        SongEntity song = songRepository.findByNameAndUser(name, user).orElseThrow(() ->
                new SongNotFoundException("Song not found"));  // Get user song

        String filename;

        if (!audioFileService.existsBySong(song)) {
            // Generate filename
            filename = fileUtils.generateRandomUUID();
            // Save file info in sql
            audioFileService.saveFile(filename, file, song);
        } else {
            filename = audioFileService.getBySong(song).getFilename();
        }
        // Save file data in minio storage
        minioFileService.saveFile(minioConfig.getAudioBucket(), filename, file);

        return Map.of("name", name);
    }

    @Transactional
    @Override
    public Map<String, String> addAvatarByUserSongName(String authHeader, String name, MultipartFile file) throws InvalidExtensionException {
        if (!fileUtils.isValidExtension(file.getOriginalFilename()))
            throw new InvalidExtensionException("Invalid file extension (must be png or jpg)");

        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);
        SongEntity song = songRepository.findByNameAndUser(name, user).orElseThrow(() ->
                new SongNotFoundException("Song not found"));  // Get user song

        String filename;

        if (!avatarService.existsByEntity(song)) {
            // Generate filename
            filename = fileUtils.generateRandomUUID();
            // Save file info in sql
            avatarService.saveAvatar(file, filename, song);
        } else {
            filename = ((UserAvatarEntity) avatarService.getByEntity(user)).getFilename();
        }

        // Save file data in minio storage
        minioFileService.saveFile(minioConfig.getSongAvatarBucket(), filename, file);

        return Map.of("name", name);
    }

    @Override
    public byte[] getSongAvatarBySongName(String username, String name) throws MinioFileException {
        UserEntity user = userService.getUserByUsername(username);
        SongEntity song = songRepository.findByNameAndUser(name, user).orElseThrow(() ->
                new SongNotFoundException("Song not found")); // Get user song

        SongAvatarEntity songAvatar = song.getSongAvatar();

        if (songAvatar == null) throw new MinioFileException("File not found");

        return minioFileService.getFileContent(minioConfig.getSongAvatarBucket(), songAvatar.getFilename());
    }
}
