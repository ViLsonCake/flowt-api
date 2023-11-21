package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.dto.SongNameDto;
import project.vilsoncake.Flowt.entity.SongEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.exception.InvalidExtensionException;
import project.vilsoncake.Flowt.exception.MinioFileException;
import project.vilsoncake.Flowt.exception.SongAlreadyExistByUserException;
import project.vilsoncake.Flowt.exception.SongNotFoundException;
import project.vilsoncake.Flowt.properties.MinioProperties;
import project.vilsoncake.Flowt.repository.SongRepository;
import project.vilsoncake.Flowt.service.MinioFileService;
import project.vilsoncake.Flowt.service.ReportService;
import project.vilsoncake.Flowt.service.SongChangeService;
import project.vilsoncake.Flowt.service.UserService;
import project.vilsoncake.Flowt.utils.AuthUtils;
import project.vilsoncake.Flowt.utils.FileUtils;

import java.util.Map;

import static project.vilsoncake.Flowt.entity.enumerated.ReportContentType.CONTENT;
import static project.vilsoncake.Flowt.entity.enumerated.ReportContentType.NAME;
import static project.vilsoncake.Flowt.entity.enumerated.WhomReportType.SONG;

@Service
@RequiredArgsConstructor
public class SongChangeServiceImpl implements SongChangeService {

    private final SongRepository songRepository;
    private final UserService userService;
    private final MinioFileService minioFileService;
    private final ReportService reportService;
    private final AuthUtils authUtils;
    private final FileUtils fileUtils;
    private final MinioProperties minioProperties;

    @Transactional
    @Override
    public Map<String, String> addAvatarByUserSongName(String authHeader, String name, MultipartFile file) throws InvalidExtensionException {
        if (file.getOriginalFilename() != null && !fileUtils.isValidAvatarExtension(file.getOriginalFilename())) {
            throw new InvalidExtensionException("Invalid file extension (must be png or jpg)");
        }

        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);
        SongEntity song = findByNameAndUser(name, user);
        song.getSongAvatar().setSize(String.valueOf(file.getSize()));
        String filename = song.getSongAvatar().getFilename();

        // Save file data in minio storage
        minioFileService.saveFile(minioProperties.getSongAvatarBucket(), filename, file);

        return Map.of("name", name);
    }

    @Transactional
    @Override
    public Map<String, String> saveNewAudioFile(String authHeader, String name, MultipartFile file) throws InvalidExtensionException, MinioFileException {
        if (file.getOriginalFilename() != null && !fileUtils.isValidAudioFileExtension(file.getOriginalFilename())) {
            throw new InvalidExtensionException("Invalid file extension (must be .mp3, .wav. or .ogg)");
        }

        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);
        SongEntity song = findByNameAndUser(name, user);
        song.getAudioFile().setSize(String.valueOf(file.getSize()));
        String filename = song.getAudioFile().getFilename();

        // Save file data in minio storage
        minioFileService.saveFile(minioProperties.getAudioBucket(), filename, file);

        reportService.cancelReportByWhomTypeAndContentTypeAndContentTypeNameAndWhom(SONG, CONTENT, song.getName(), user);

        return Map.of("message", "Audio file successfully added");
    }

    @Override
    public Map<String, String> changeSongName(String authHeader, SongNameDto songNameDto) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);
        SongEntity song = findByNameAndUser(songNameDto.getCurrentName(), user);

        if (songRepository.existsByNameAndUser(songNameDto.getNewName(), user)) {
            throw new SongAlreadyExistByUserException("User have name with same name");
        }

        reportService.cancelReportByWhomTypeAndContentTypeAndContentTypeNameAndWhom(SONG, NAME, songNameDto.getCurrentName(), user);

        song.setName(songNameDto.getNewName());
        songRepository.save(song);

        return Map.of("name", songNameDto.getNewName());
    }

    @Override
    public SongEntity findByNameAndUser(String name, UserEntity user) {
        return songRepository.findByNameAndUser(name, user).orElseThrow(() ->
                new SongNotFoundException("Song not found")); // Get user song
    }
}
