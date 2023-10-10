package project.vilsoncake.Flowt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.dto.PlaylistDto;
import project.vilsoncake.Flowt.dto.PlaylistNameDto;
import project.vilsoncake.Flowt.exception.InvalidExtensionException;
import project.vilsoncake.Flowt.service.PlaylistChangeService;
import project.vilsoncake.Flowt.service.PlaylistService;

import java.util.Map;

@RestController
@RequestMapping("/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playListService;
    private final PlaylistChangeService playlistChangeService;

    @PostMapping
    public ResponseEntity<Map<String, String>> createNewPlaylist(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false, defaultValue = "") String authHeader,
            @RequestBody PlaylistDto playlistDto
    ) {
        return ResponseEntity.ok(playListService.createNewPlaylist(authHeader, playlistDto));
    }

    @PostMapping("/avatar/{playlistName}")
    public ResponseEntity<Map<String, String>> addAvatarToPlaylist(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @PathVariable("playlistName") String name,
            @RequestParam("file") MultipartFile avatar
    ) throws InvalidExtensionException {
        return ResponseEntity.ok(playListService.addAvatarToPlaylist(authHeader, name, avatar));
    }

    @PostMapping("/{playlistName}/{songAuthor}/{songName}")
    public ResponseEntity<Map<String, String>> addSongToPlaylist(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @PathVariable("playlistName") String playlistName,
            @PathVariable("songAuthor") String songAuthor,
            @PathVariable("songName") String songName
    ) {
        return ResponseEntity.ok(playListService.addSongToPlaylist(authHeader, playlistName, songAuthor, songName));
    }

    @PatchMapping
    public ResponseEntity<Map<String, String>> changePlaylistName(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @RequestBody PlaylistNameDto playlistNameDto
            ) {
        return ResponseEntity.ok(playlistChangeService.changePlaylistName(authHeader, playlistNameDto));
    }

    @PatchMapping("/{playlistName}")
    public ResponseEntity<Map<String, Boolean>> changeAccessModifier(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @PathVariable("playlistName") String playlistName
    ) {
        return ResponseEntity.ok(playlistChangeService.changePlaylistAccessModifier(authHeader, playlistName));
    }

    @DeleteMapping("/{playlistName}/{songAuthor}/{songName}")
    public ResponseEntity<Map<String, String>> removeSongFromPlaylist(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @PathVariable("playlistName") String playlistName,
            @PathVariable("songAuthor") String songAuthor,
            @PathVariable("songName") String songName
    ) {
        return ResponseEntity.ok(playListService.removeSongFromPlaylist(authHeader, playlistName, songAuthor, songName));
    }
}
