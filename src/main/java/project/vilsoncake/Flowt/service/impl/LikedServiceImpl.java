package project.vilsoncake.Flowt.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.vilsoncake.Flowt.dto.LikedSongsDto;
import project.vilsoncake.Flowt.entity.LikedEntity;
import project.vilsoncake.Flowt.entity.SongEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.repository.LikedRepository;
import project.vilsoncake.Flowt.service.LikedService;
import project.vilsoncake.Flowt.service.SongService;
import project.vilsoncake.Flowt.service.UserService;
import project.vilsoncake.Flowt.utils.AuthUtils;
import project.vilsoncake.Flowt.utils.PageUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikedServiceImpl implements LikedService {

    private final LikedRepository likedRepository;
    private final SongService songService;
    private final UserService userService;
    private final AuthUtils authUtils;
    private final PageUtils pageUtils;

    @Transactional
    @Override
    public Map<String, String> addSongToLiked(String authHeader, String author, String name) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);
        UserEntity authorUser = userService.getUserByUsername(author);
        SongEntity song = songService.findByNameAndUser(name, authorUser);

        // if user don't have liken entity, create
        if (user.getLiked() == null) createUserLikedEntity(user);

        Map<String, String> response = new HashMap<>();

        if (user.getLiked().getSongs().contains(song)) {
            // Add song to user liked
            LikedEntity liked = user.getLiked();
            liked.getSongs().add(song);
            likedRepository.save(liked);
            response.put("message", String.format("Song '%s' added to liked %s username", name, username));
        } else {
            response.put("message", String.format("Song '%s' already in liked %s username", name, username));
        }

        return response;
    }

    @Override
    public Map<String, String> removeSongFromLiked(String authHeader, String author, String name) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);
        SongEntity song = songService.findByNameAndUser(name, user);

        LikedEntity liked = user.getLiked();
        Map<String, String> response = new HashMap<>();

        if (liked.getSongs().contains(song)) {
            // Create new liked list without specified song
            List<SongEntity> songsWithoutDeleted = new ArrayList<>();
            liked.getSongs().forEach(likedSong -> {
                if (!likedSong.getName().equalsIgnoreCase(name) && !likedSong.getUser().getUsername().equals(author))
                    songsWithoutDeleted.add(likedSong);
            });
            liked.setSongs(songsWithoutDeleted);
            likedRepository.save(liked);
            response.put("message", String.format("Song '%s' removed from liked %s username", name, username));
        } else {
            response.put("message", String.format("Song '%s' not in liked %s username", name, username));
        }

        return response;
    }

    @Override
    public boolean createUserLikedEntity(UserEntity user) {
        LikedEntity liked = new LikedEntity(user);
        likedRepository.save(liked);
        return true;
    }

    @Override
    public LikedSongsDto getLikedSongs(String authHeader, int page, int size) {
        if (page < 0 || size < 1) return null;

        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);
        List<SongEntity> songs = user.getLiked().getSongs();
        Page<SongEntity> songPages = pageUtils.convertToPage(songs, PageRequest.of(page, size));

        Map<String, String> authorWithSong = new HashMap<>();

        // Convert to map<Author, SongName>
        songPages.getContent().forEach(likedSong -> {
            authorWithSong.put(likedSong.getUser().getUsername(), likedSong.getName());
        });

        return new LikedSongsDto(songPages.getTotalPages(), authorWithSong);
    }
}
