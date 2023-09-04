package project.vilsoncake.Flowt.service;

import project.vilsoncake.Flowt.entity.LikedEntity;
import project.vilsoncake.Flowt.entity.UserEntity;

import java.util.Map;

public interface LikedService {
    Map<String, String> addSongToLiked(String username, String name);
    Map<String, String> removeSongFromLiked(String username, String name);
    boolean createLikedEntityFromUser(UserEntity user);
    Map<String, Map<String, String>> getLikedSongs(String authHeader);
}
