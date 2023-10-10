package project.vilsoncake.Flowt.service;

import project.vilsoncake.Flowt.dto.PlaylistNameDto;
import project.vilsoncake.Flowt.entity.PlaylistEntity;
import project.vilsoncake.Flowt.entity.UserEntity;

import java.util.Map;

public interface PlaylistChangeService {
    Map<String, String> changePlaylistName(String authHeader, PlaylistNameDto playlistNameDto);
    Map<String, Boolean> changePlaylistAccessModifier(String authHeader, String playlistName);
    PlaylistEntity getPlaylistByUserAndName(UserEntity user, String name);
}
