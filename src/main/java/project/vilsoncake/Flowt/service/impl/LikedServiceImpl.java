package project.vilsoncake.Flowt.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.vilsoncake.Flowt.dto.LikedSongsDto;
import project.vilsoncake.Flowt.dto.SongDto;
import project.vilsoncake.Flowt.entity.SongEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.service.LikedService;
import project.vilsoncake.Flowt.service.SongService;
import project.vilsoncake.Flowt.service.UserService;
import project.vilsoncake.Flowt.utils.AuthUtils;
import project.vilsoncake.Flowt.utils.PageUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikedServiceImpl implements LikedService {

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
        Map<String, String> response = new HashMap<>();

        if (!user.getLiked().getSongs().contains(song)) {
            user.getLiked().getSongs().add(song);
            response.put("message", String.format("Song '%s' added to liked %s username", name, username));
        } else {
            response.put("message", String.format("Song '%s' already in liked %s username", name, username));
        }

        return response;
    }

    @Transactional
    @Override
    public Map<String, String> removeSongFromLiked(String authHeader, String author, String name) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);
        UserEntity songAuthor = userService.getUserByUsername(author);
        SongEntity song = songService.findByNameAndUser(name, songAuthor);
        Map<String, String> response = new HashMap<>();

        if (user.getLiked().getSongs().contains(song)) {
            user.getLiked().getSongs().remove(song);
            response.put("message", String.format("Song '%s' removed from liked", name));
        } else {
            response.put("message", String.format("Song '%s' not in liked", name));
        }

        return response;
    }

    @Override
    public LikedSongsDto getLikedSongs(String authHeader, int page, int size) {
        if (page < 0 || size < 1) {
            return null;
        }

        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);
        List<SongEntity> songs = user.getLiked().getSongs();
        Page<SongEntity> songPages = pageUtils.convertSongsToPage(songs, PageRequest.of(page, size));

        return new LikedSongsDto(
                songPages.getTotalPages(),
                songPages.getContent().stream().map(SongDto::fromSongEntity).toList()
        );
    }
}
