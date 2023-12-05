package project.vilsoncake.Flowt.service;

import project.vilsoncake.Flowt.dto.SavePlaylistDto;
import project.vilsoncake.Flowt.dto.SavedPlaylistsDto;

import java.util.Map;

public interface SavedPlaylistService {
    SavedPlaylistsDto getUserSavedPlaylists(String authHeader, int page, int size);
    Map<String, String> addPlaylistToSaved(String authHeader, SavePlaylistDto savePlaylistDto);
    Map<String, String> removePlaylistFromSaved(String authHeader, SavePlaylistDto savePlaylistDto);
}
