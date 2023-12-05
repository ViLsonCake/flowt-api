package project.vilsoncake.Flowt.service;

import java.util.Map;

public interface LastListenedPlaylistsService {
    Map<String, String> addPlaylistToLastListened(String authHeader, String name, String playlistName);
}
