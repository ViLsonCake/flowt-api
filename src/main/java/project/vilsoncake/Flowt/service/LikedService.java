package project.vilsoncake.Flowt.service;

import project.vilsoncake.Flowt.entity.UserEntity;

import java.util.Map;

public interface LikedService {
    Map<String, String> addSongToLiked(String authHeader, String username, String name);
    Map<String, String> removeSongFromLiked(String authHeader, String username, String name);
    boolean createLikedEntityFromUser(UserEntity user);
    Map<String, Map<String, String>> getLikedSongs(String authHeader);
}