package project.vilsoncake.Flowt.service;

import project.vilsoncake.Flowt.dto.LikedSongsDto;

import java.util.Map;

public interface LikedService {
    Map<String, String> addSongToLiked(String authHeader, String username, String name);
    Map<String, String> removeSongFromLiked(String authHeader, String username, String name);
    LikedSongsDto getLikedSongs(String authHeader, int page, int size);
}
