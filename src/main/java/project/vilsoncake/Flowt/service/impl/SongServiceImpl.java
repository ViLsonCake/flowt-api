package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.vilsoncake.Flowt.config.MinioConfig;
import project.vilsoncake.Flowt.dto.SongRequest;
import project.vilsoncake.Flowt.dto.SongsResponse;
import project.vilsoncake.Flowt.dto.SubstringDto;
import project.vilsoncake.Flowt.entity.AudioFileEntity;
import project.vilsoncake.Flowt.entity.SongAvatarEntity;
import project.vilsoncake.Flowt.entity.SongEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.exception.MinioFileException;
import project.vilsoncake.Flowt.exception.SongAlreadyExistByUserException;
import project.vilsoncake.Flowt.exception.SongNotFoundException;
import project.vilsoncake.Flowt.exception.UserEmailNotVerifiedException;
import project.vilsoncake.Flowt.repository.SongRepository;
import project.vilsoncake.Flowt.service.*;
import project.vilsoncake.Flowt.utils.AuthUtils;
import project.vilsoncake.Flowt.utils.MailUtils;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final UserService userService;
    private final AuthUtils authUtils;
    private final MailUtils mailUtils;
    private final AvatarService songAvatarService;
    private final LastListenedService lastListenedService;
    private final MinioFileService minioFileService;
    private final MinioConfig minioConfig;

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

    @Override
    public boolean removeSongAvatarByUserAndName(UserEntity user, String name) {
        SongEntity song = findByNameAndUser(name, user);
        return songAvatarService.deleteAvatar(song);
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
    public SongsResponse getSongsBySubstring(SubstringDto substringDto, int page, int size) {
        Page<SongEntity> songs = songRepository.findByNameContainingIgnoreCase(substringDto.getSubstring(), PageRequest.of(page, size));

        return new SongsResponse(
                songs.getTotalPages(),
                songs.getContent()
        );
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
    public boolean removeUserSongByUserAndName(UserEntity user, String name) {
        SongEntity song = findByNameAndUser(name, user);
        songRepository.delete(song);
        return true;
    }

    @Transactional
    @Override
    public byte[] getSongAudioFile(String username, String name) throws MinioFileException {
        UserEntity user = userService.getUserByUsername(username);
        SongEntity song = findByNameAndUser(name, user);

        if (song != null && song.getAudioFile() != null) {
            incrementSongListens(song, user);
            lastListenedService.addSongToLastListenedByUser(user, song);

            return minioFileService.getFileContent(minioConfig.getAudioBucket(), song.getAudioFile().getFilename());
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

    @Transactional
    @Override
    public void incrementSongListens(SongEntity song, UserEntity user) {
        song.setListens(song.getListens() + 1);
        mailUtils.sendCongratulationsMessagesIfNeed(song, user);
    }

    @Override
    public SongEntity findByNameAndUser(String name, UserEntity user) {
        return songRepository.findByNameAndUser(name, user).orElseThrow(() ->
                new SongNotFoundException("Song not found")); // Get user song
    }
}
