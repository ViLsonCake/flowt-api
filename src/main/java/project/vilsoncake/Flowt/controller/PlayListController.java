package project.vilsoncake.Flowt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.dto.PlaylistDto;
import project.vilsoncake.Flowt.exception.InvalidExtensionException;
import project.vilsoncake.Flowt.service.PlaylistService;

import java.util.Map;

@RestController
@RequestMapping("/playlists")
@RequiredArgsConstructor
public class PlayListController {

    private final PlaylistService playListService;

    @PostMapping
    public ResponseEntity<Map<String, String>> createNewPlaylist(
            @RequestHeader(value = "Authorization", required = false, defaultValue = "") String authHeader,
            @RequestBody PlaylistDto playlistDto
    ) {
        return ResponseEntity.ok(playListService.createNewPlaylist(authHeader, playlistDto));
    }

    @PostMapping("/avatar/{playlistName}")
    public ResponseEntity<Map<String, String>> addAvatarToPlaylist(
            @RequestHeader(value = "Authorization", required = false, defaultValue = "") String authHeader,
            @PathVariable("playlistName") String name,
            @RequestParam("file") MultipartFile avatar
    ) throws InvalidExtensionException {
        return ResponseEntity.ok(playListService.addAvatarToPlaylist(authHeader, name, avatar));
    }

    @PostMapping("/{playlistName}/{songAuthor}/{songName}")
    public ResponseEntity<Map<String, String>> addSongToPlaylist(
            @RequestHeader(value = "Authorization", required = false, defaultValue = "") String authHeader,
            @PathVariable("playlistName") String playlistName,
            @PathVariable("songAuthor") String songAuthor,
            @PathVariable("songName") String songName
    ) {
        return ResponseEntity.ok(playListService.addSongToPlaylist(authHeader, playlistName, songAuthor, songName));
    }

    @DeleteMapping("/{playlistName}/{songAuthor}/{songName}")
    public ResponseEntity<Map<String, String>> removeSongFromPlaylist(
            @RequestHeader(value = "Authorization", required = false, defaultValue = "") String authHeader,
            @PathVariable("playlistName") String playlistName,
            @PathVariable("songAuthor") String songAuthor,
            @PathVariable("songName") String songName
    ) {
        return ResponseEntity.ok(playListService.removeSongFromPlaylist(authHeader, playlistName, songAuthor, songName));
    }
}
