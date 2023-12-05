package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.vilsoncake.Flowt.dto.PlaylistDto;
import project.vilsoncake.Flowt.dto.SavePlaylistDto;
import project.vilsoncake.Flowt.dto.SavedPlaylistsDto;
import project.vilsoncake.Flowt.entity.PlaylistEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.service.PlaylistService;
import project.vilsoncake.Flowt.service.SavedPlaylistService;
import project.vilsoncake.Flowt.service.UserService;
import project.vilsoncake.Flowt.utils.AuthUtils;
import project.vilsoncake.Flowt.utils.PageUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SavedPlaylistServiceImpl implements SavedPlaylistService {

    private final UserService userService;
    private final PlaylistService playlistService;
    private final AuthUtils authUtils;
    private final PageUtils pageUtils;

    @Override
    public SavedPlaylistsDto getUserSavedPlaylists(String authHeader, int page, int size) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);
        Page<PlaylistEntity> playlistPages = pageUtils.convertPlaylistsToPage(user.getSavedPlaylists().getPlaylists(), PageRequest.of(page, size));

        return new SavedPlaylistsDto(
                playlistPages.getTotalPages(),
                playlistPages.getContent().stream().map(PlaylistDto::fromPlaylistEntity).toList()
        );
    }

    @Transactional
    @Override
    public Map<String, String> addPlaylistToSaved(String authHeader, SavePlaylistDto savePlaylistDto) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity authenticatedUser = userService.getUserByUsername(username);
        UserEntity playlistOwner = userService.getUserByUsername(savePlaylistDto.getUsername());
        PlaylistEntity playlist = playlistService.getPlaylistByUserAndName(playlistOwner, savePlaylistDto.getPlaylistName());
        Map<String, String> response = new HashMap<>();

        if (!authenticatedUser.getSavedPlaylists().getPlaylists().contains(playlist)) {
            authenticatedUser.getSavedPlaylists().getPlaylists().add(playlist);
            response.put("message", String.format("Playlist '%s' added to saved", playlist.getName()));
        } else {
            response.put("message", String.format("Playlist '%s' already in saved", playlist.getName()));
        }

        return response;
    }

    @Transactional
    @Override
    public Map<String, String> removePlaylistFromSaved(String authHeader, SavePlaylistDto savePlaylistDto) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity authenticatedUser = userService.getUserByUsername(username);
        UserEntity playlistOwner = userService.getUserByUsername(savePlaylistDto.getUsername());
        PlaylistEntity playlist = playlistService.getPlaylistByUserAndName(playlistOwner, savePlaylistDto.getPlaylistName());
        Map<String, String> response = new HashMap<>();

        if (authenticatedUser.getSavedPlaylists().getPlaylists().contains(playlist)) {
            authenticatedUser.getSavedPlaylists().getPlaylists().remove(playlist);
            response.put("message", String.format("Playlist '%s' removed from saved", playlist.getName()));
        } else {
            response.put("message", String.format("Playlist '%s' not in saved", playlist.getName()));
        }

        return response;
    }
}
