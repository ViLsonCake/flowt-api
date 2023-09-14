package project.vilsoncake.Flowt.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.config.MinioConfig;
import project.vilsoncake.Flowt.dto.SongRequest;
import project.vilsoncake.Flowt.dto.SongsResponse;
import project.vilsoncake.Flowt.entity.*;
import project.vilsoncake.Flowt.exception.*;
import project.vilsoncake.Flowt.repository.SongRepository;
import project.vilsoncake.Flowt.service.*;
import project.vilsoncake.Flowt.utils.AuthUtils;
import project.vilsoncake.Flowt.utils.FileUtils;

import java.util.List;
import java.util.Map;
import java.util.Random;

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
    public Map<String, String> saveNewSongEntity(String authHeader, SongRequest songRequest) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);

        // If user email not verified, he can't add a song
        if (!user.isEmailVerify()) throw new UserEmailNotVerifiedException("User email not verified");

        // If user have song with the same name
        if (songRepository.existsByNameAndUser(songRequest.getName(), user)) throw new SongAlreadyExistByUserException("User have name with same name");

        // Save new song
        SongEntity song = new SongEntity();
        song.setName(songRequest.getName());
        song.setIssueYear(songRequest.getIssueYear());
        song.setGenre(songRequest.getGenre());
        song.setUser(user);
        song.setListens(0L);
        songRepository.save(song);

        return Map.of("name", songRequest.getName());
    }

    @Transactional
    @Override
    public Map<String, String> saveNewAudioFile(String authHeader, String name, MultipartFile file) throws InvalidExtensionException, MinioFileException {
        if (file.getOriginalFilename() != null && !fileUtils.isValidAudioFileExtension(file.getOriginalFilename())) {
            throw new InvalidExtensionException("Invalid file extension (must be mp3)");
        }

        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);
        SongEntity song = findByNameAndUser(name, user);

        if (audioFileService.existsBySong(song)) {
            throw new MinioFileException("Song already have a file");
        }
        // Generate filename
        String filename = fileUtils.generateRandomUUID();
        // Save file info in sql
        audioFileService.saveFile(filename, file, song);

        // Save file data in minio storage
        minioFileService.saveFile(minioConfig.getAudioBucket(), filename, file);

        return Map.of("name", name);
    }

    @Transactional
    @Override
    public Map<String, String> addAvatarByUserSongName(String authHeader, String name, MultipartFile file) throws InvalidExtensionException {
        if (file.getOriginalFilename() != null && !fileUtils.isValidAvatarExtension(file.getOriginalFilename()))
            throw new InvalidExtensionException("Invalid file extension (must be png or jpg)");

        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);
        SongEntity song = findByNameAndUser(name, user);

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
    public SongsResponse getSongsByUser(String authHeader, int page, int size) {
        if (page < 0 || size < 1) return null;

        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);
        Page<SongEntity> pageSongs = songRepository.findAllByUser(user, PageRequest.of(page, size));

        return new SongsResponse(pageSongs.getTotalPages(), pageSongs.toList());
    }

    @Override
    public SongsResponse getSongsByGenre(String genre, int page, int size) {
        if (page < 0 || size < 1) return null;

        Page<SongEntity> songsOnPage = songRepository.findAllByGenre(genre, PageRequest.of(page, size));

        return new SongsResponse(songsOnPage.getTotalPages(), songsOnPage.getContent());
    }

    @Override
    public SongEntity getSongInfo(String username, String name) {
        UserEntity user = userService.getUserByUsername(username);
        return findByNameAndUser(name, user);
    }

    @Override
    public SongEntity getRandomSongInfoByGenre(String genre) {
        List<SongEntity> songs = songRepository.findAllByGenre(genre);
        if (songs.size() == 1) return songs.get(0);
        if (songs.isEmpty()) return null;

        int randomSongIndex = new Random().nextInt(0, songs.size() - 1);

        // Get random song
        return songs.get(randomSongIndex);
    }

    @Override
    public Map<String, String> removeUserSong(String authHeader, String name) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);
        SongEntity song = findByNameAndUser(name, user);

        // Remove audio file from MinIO
        minioFileService.removeFile(minioConfig.getAudioBucket(), song.getAudioFile().getFilename());
        // Remove song with avatar and audio from postgres
        songRepository.delete(song);

        return Map.of("message", String.format("Song '%s' removed", name));
    }

    @Transactional
    @Override
    public byte[] getSongAudioFile(String username, String name) throws MinioFileException {
        UserEntity user = userService.getUserByUsername(username);
        SongEntity song = findByNameAndUser(name, user);

        if (song != null && song.getAudioFile() != null) {
            // Increment song listens
            incrementSongListens(song);
            AudioFileEntity audioFile = song.getAudioFile();

            return minioFileService.getFileContent(minioConfig.getAudioBucket(), audioFile.getFilename());
        }

        throw new MinioFileException("File not found");
    }

    @Transactional
    @Override
    public byte[] getSongAvatarBySongName(String username, String name) throws MinioFileException {
        UserEntity user = userService.getUserByUsername(username);
        SongEntity song = findByNameAndUser(name, user);

        SongAvatarEntity songAvatar = song.getSongAvatar();

        if (songAvatar == null) throw new MinioFileException("File not found");

        return minioFileService.getFileContent(minioConfig.getSongAvatarBucket(), songAvatar.getFilename());
    }

    @Override
    public void incrementSongListens(SongEntity song) {
        song.setListens(song.getListens() + 1);
        songRepository.save(song);
    }

    @Override
    public SongEntity findByNameAndUser(String name, UserEntity user) {
        return songRepository.findByNameAndUser(name, user).orElseThrow(() ->
                new SongNotFoundException("Song not found")); // Get user song
    }
}
