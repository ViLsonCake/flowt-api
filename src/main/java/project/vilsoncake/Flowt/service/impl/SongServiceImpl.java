package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.vilsoncake.Flowt.dto.SongDto;
import project.vilsoncake.Flowt.dto.SongRequest;
import project.vilsoncake.Flowt.dto.SongsResponse;
import project.vilsoncake.Flowt.dto.SubstringDto;
import project.vilsoncake.Flowt.entity.*;
import project.vilsoncake.Flowt.entity.enumerated.Country;
import project.vilsoncake.Flowt.entity.enumerated.Genre;
import project.vilsoncake.Flowt.exception.MinioFileException;
import project.vilsoncake.Flowt.exception.SongAlreadyExistByUserException;
import project.vilsoncake.Flowt.exception.SongNotFoundException;
import project.vilsoncake.Flowt.exception.UserEmailNotVerifiedException;
import project.vilsoncake.Flowt.properties.MinioProperties;
import project.vilsoncake.Flowt.repository.SongRepository;
import project.vilsoncake.Flowt.service.*;
import project.vilsoncake.Flowt.utils.AuthUtils;
import project.vilsoncake.Flowt.utils.MailUtils;
import project.vilsoncake.Flowt.utils.SongUtils;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final UserService userService;
    private final AuthUtils authUtils;
    private final MailUtils mailUtils;
    private final SongUtils songUtils;
    private final AvatarService songAvatarService;
    private final LastListenedService lastListenedService;
    private final MinioFileService minioFileService;
    private final MinioProperties minioProperties;

    @Override
    public Map<String, String> saveNewSongEntity(String authHeader, SongRequest songRequest) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);

        // If user email not verified, he can't add a song
        if (!user.isEmailVerify()) {
            throw new UserEmailNotVerifiedException("User email not verified");
        }

        // If user have song with the same name
        if (songRepository.existsByNameAndUser(songRequest.getName(), user)) {
            throw new SongAlreadyExistByUserException("User have name with same name");
        }

        // Save new song
        SongEntity song = new SongEntity();
        song.setName(songRequest.getName());
        song.setIssueYear(songRequest.getIssueYear());
        song.setGenre(Genre.valueOf(songRequest.getGenre().toUpperCase()));
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
    public SongsResponse getAuthenticatedUserSongs(String authHeader, int page, int size) {
        if (page < 0 || size < 1) {
            return null;
        }

        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);
        Page<SongEntity> songsOnPage = songRepository.findAllByUser(user, PageRequest.of(page, size));

        return new SongsResponse(
                songsOnPage.getTotalPages(),
                songsOnPage.getContent().stream().map(SongDto::fromSongEntity).toList()
        );
    }

    @Override
    public SongsResponse getSongsByUsername(String username, int page, int size) {
        if (page < 0 || size < 1) {
            return null;
        }

        UserEntity user = userService.getUserByUsername(username);
        Page<SongEntity> songsOnPage = songRepository.findAllByUser(user, PageRequest.of(page, size));

        return new SongsResponse(
                songsOnPage.getTotalPages(),
                songsOnPage.getContent().stream().map(SongDto::fromSongEntity).toList()
        );
    }

    @Override
    public SongsResponse getSongsByGenre(String genre, int page, int size) {
        if (page < 0 || size < 1) {
            return null;
        }

        Page<SongEntity> songsOnPage = songRepository.findAllByGenre(Genre.valueOf(genre.toUpperCase()), PageRequest.of(page, size));

        return new SongsResponse(
                songsOnPage.getTotalPages(),
                songsOnPage.getContent().stream().map(SongDto::fromSongEntity).toList()
        );
    }

    @Override
    public SongsResponse getSongsBySubstring(SubstringDto substringDto, int page, int size) {
        Page<SongEntity> songsOnPage = songRepository.findByNameContainingIgnoreCaseOrderByListensDesc(substringDto.getSubstring(), PageRequest.of(page, size));

        return new SongsResponse(
                songsOnPage.getTotalPages(),
                songsOnPage.getContent().stream().map(SongDto::fromSongEntity).toList()
        );
    }

    @Override
    public SongEntity getSongInfo(String username, String name) {
        UserEntity user = userService.getUserByUsername(username);
        return findByNameAndUser(name, user);
    }

    @Override
    public SongEntity getRandomSongInfoByGenre(String genre) {
        List<SongEntity> songs = songRepository.findAllByGenre(Genre.valueOf(genre.toUpperCase()));
        if (songs.size() == 1) {
            return songs.get(0);
        }
        if (songs.isEmpty()) {
            return null;
        }

        int randomSongIndex = new Random().nextInt(0, songs.size() - 1);
        return songs.get(randomSongIndex);
    }

    @Override
    public SongEntity getRandomUserSong(UserEntity user) {
        return songRepository.getRandomUserSong(user);
    }

    @Override
    public List<SongEntity> getRandomMostListenedSongsByGenres(List<Genre> genres) {
        List<SongEntity> randomSongs = new ArrayList<>();
        genres.forEach(genre -> {
            List<SongEntity> mostListenedSongsByGenre = songRepository.getMostListenedSongsByGenre(genre);
            List<SongEntity> randomSongsFromList = songUtils.getRandomSongsFromList(mostListenedSongsByGenre);
            randomSongs.addAll(randomSongsFromList);
        });
        Collections.shuffle(randomSongs);
        return randomSongs;
    }

    @Override
    public List<SongEntity> getMostPopularSongs(UserEntity user) {
        return songRepository.getMostListenedSongsByUser(user);
    }

    @Override
    public Map<String, String> removeUserSong(String authHeader, String name) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);
        SongEntity song = findByNameAndUser(name, user);

        // Remove audio file from MinIO
        minioFileService.removeFile(minioProperties.getAudioBucket(), song.getAudioFile().getFilename());
        // Remove song with avatar and audio from postgres
        songRepository.delete(song);

        return Map.of("message", String.format("Song '%s' removed", name));
    }

    @Override
    public Map<String, String> updateListenerAndSongStatistic(String authHeader, String author, String name) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);
        SongEntity song = findByNameAndUser(name, user);
        Country userCountry = Country.valueOf(user.getRegion().toUpperCase());

        // Update song statistic
        List<ListeningEntity> listeningEntities = song.getRegionStatistic().getListeningEntities();
        listeningEntities.add(new ListeningEntity(
                userCountry.getRegion(),
                userCountry,
                user.getUsername(),
                song.getRegionStatistic()
        ));

        // Update last listened
        lastListenedService.addSongToLastListenedByUser(user, song);

        // Update user statistic
        List<ListenedEntity> listenedEntities = user.getListenedStatistic().getListenedEntities();
        listenedEntities.add(new ListenedEntity(
                song.getName(),
                song.getGenre(),
                song.getUser().getUsername()
        ));

        return Map.of("message", "Statistic updated");
    }

    @Transactional
    @Override
    public boolean removeUserSongByUserAndName(UserEntity user, String name) {
        SongEntity song = findByNameAndUser(name, user);
        minioFileService.removeFile(minioProperties.getAudioBucket(), song.getAudioFile().getFilename());
        minioFileService.removeFile(minioProperties.getSongAvatarBucket(), song.getSongAvatar().getFilename());
        songRepository.delete(song);
        return true;
    }

    @Transactional
    @Override
    public byte[] getSongAudioFile(String author, String name) throws MinioFileException {
        UserEntity user = userService.getUserByUsername(author);
        SongEntity song = findByNameAndUser(name, user);

        if (song == null || song.getAudioFile() == null) {
            throw new MinioFileException("File not found");
        }

        incrementSongListens(song, user);

        return minioFileService.getFileContent(minioProperties.getAudioBucket(), song.getAudioFile().getFilename());
    }

    @Transactional
    @Override
    public byte[] getSongAvatarBySongName(String username, String name) throws MinioFileException {
        UserEntity user = userService.getUserByUsername(username);
        SongEntity song = findByNameAndUser(name, user);

        SongAvatarEntity songAvatar = song.getSongAvatar();

        if (songAvatar == null) {
            throw new MinioFileException("File not found");
        }

        return minioFileService.getFileContent(minioProperties.getSongAvatarBucket(), songAvatar.getFilename());
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
