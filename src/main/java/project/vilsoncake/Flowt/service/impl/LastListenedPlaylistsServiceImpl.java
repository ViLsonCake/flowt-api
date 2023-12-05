package project.vilsoncake.Flowt.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.vilsoncake.Flowt.constant.NumberConst;
import project.vilsoncake.Flowt.entity.PlaylistEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.service.LastListenedPlaylistsService;
import project.vilsoncake.Flowt.service.PlaylistService;
import project.vilsoncake.Flowt.service.UserService;
import project.vilsoncake.Flowt.utils.AuthUtils;

import java.util.List;
import java.util.Map;

import static project.vilsoncake.Flowt.constant.NumberConst.LAST_LISTENED_COUNT;

@Service
@RequiredArgsConstructor
public class LastListenedPlaylistsServiceImpl implements LastListenedPlaylistsService {

    private final UserService userService;
    private final PlaylistService playlistService;
    private final AuthUtils authUtils;

    @Transactional
    @Override
    public Map<String, String> addPlaylistToLastListened(String authHeader, String name, String playlistName) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity authenticatedUser = userService.getUserByUsername(username);
        UserEntity playlistOwner = userService.getUserByUsername(name);
        PlaylistEntity playlist = playlistService.getPlaylistByUserAndName(playlistOwner, playlistName);
        List<PlaylistEntity> lastListenedPlaylists = authenticatedUser.getLastListenedPlaylists().getPlaylists();

        if (!lastListenedPlaylists.contains(playlist)) {
            lastListenedPlaylists.add(playlist);
        }
        if (lastListenedPlaylists.size() > LAST_LISTENED_COUNT) {
            lastListenedPlaylists.remove(0);
        }

        return Map.of("message", "Playlist added to last listened");
    }
}
