package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.vilsoncake.Flowt.entity.LikedEntity;
import project.vilsoncake.Flowt.entity.SongEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.repository.LikedRepository;
import project.vilsoncake.Flowt.service.LikedService;
import project.vilsoncake.Flowt.service.SongService;
import project.vilsoncake.Flowt.service.UserService;
import project.vilsoncake.Flowt.utils.AuthUtils;

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

    @Override
    public Map<String, String> addSongToLiked(String username, String name) {
        UserEntity user = userService.getUserByUsername(username);
        SongEntity song = songService.findByNameAndUser(name, user);

        // if user don't have liken entity, create
        if (user.getLiked() == null) createLikedEntityFromUser(user);

        // Add song to user liked
        LikedEntity liked = user.getLiked();
        liked.getSongs().add(song);
        likedRepository.save(liked);

        return Map.of("message", String.format("Song '%s' added to liked %s username", name, username));
    }

    @Override
    public Map<String, String> removeSongFromLiked(String username, String name) {
        return null; //TODO create remove song from liked method
    }

    @Override
    public boolean createLikedEntityFromUser(UserEntity user) {
        LikedEntity liked = new LikedEntity(new ArrayList<>(), user);
        likedRepository.save(liked);
        return true;
    }

    @Override
    public Map<String, Map<String, String>> getLikedSongs(String authHeader) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);

        LikedEntity liked = user.getLiked();
        Map<String, String> authorWithSong = new HashMap<>();
        // Convert to map<Author, SongName>
        liked.getSongs().forEach(likedSong -> {
            authorWithSong.put(likedSong.getUser().getUsername(), likedSong.getName());
        });

        return Map.of("songs", authorWithSong);
    }
}
