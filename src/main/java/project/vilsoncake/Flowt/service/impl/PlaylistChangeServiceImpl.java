package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.vilsoncake.Flowt.dto.PlaylistNameDto;
import project.vilsoncake.Flowt.entity.PlaylistEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.exception.PlaylistNotFoundException;
import project.vilsoncake.Flowt.repository.PlaylistRepository;
import project.vilsoncake.Flowt.service.PlaylistChangeService;
import project.vilsoncake.Flowt.service.ReportService;
import project.vilsoncake.Flowt.service.UserService;
import project.vilsoncake.Flowt.utils.AuthUtils;

import java.util.Map;

import static project.vilsoncake.Flowt.entity.enumerated.ReportContentType.NAME;
import static project.vilsoncake.Flowt.entity.enumerated.WhomReportType.PLAYLIST;

@Service
@RequiredArgsConstructor
public class PlaylistChangeServiceImpl implements PlaylistChangeService {

    private final PlaylistRepository playlistRepository;
    private final UserService userService;
    private final ReportService reportService;
    private final AuthUtils authUtils;

    @Override
    public Map<String, String> changePlaylistName(String authHeader, PlaylistNameDto playlistNameDto) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);
        PlaylistEntity playlist = getPlaylistByUserAndName(user, playlistNameDto.getPlaylistName());

        playlist.setName(playlistNameDto.getNewPlaylistName());
        playlistRepository.save(playlist);

        reportService.cancelReportByWhomTypeAndContentTypeAndContentTypeNameAndWhom(PLAYLIST, NAME, playlistNameDto.getPlaylistName(), user);

        return Map.of("name", playlist.getName());
    }

    @Override
    public Map<String, Boolean> changePlaylistAccessModifier(String authHeader, String playlistName) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);
        PlaylistEntity playlist = getPlaylistByUserAndName(user, playlistName);

        // Change access modifier
        playlist.setPrivate(!playlist.isPrivate());
        playlistRepository.save(playlist);

        return Map.of("accessModifier", playlist.isPrivate());
    }

    @Override
    public PlaylistEntity getPlaylistByUserAndName(UserEntity user, String name) {
        return playlistRepository.findByUserAndName(user, name).orElseThrow(() ->
                new PlaylistNotFoundException("Playlist not found"));
    }
}
