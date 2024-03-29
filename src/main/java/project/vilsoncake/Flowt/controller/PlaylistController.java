package project.vilsoncake.Flowt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.dto.*;
import project.vilsoncake.Flowt.exception.InvalidExtensionException;
import project.vilsoncake.Flowt.service.LastListenedPlaylistsService;
import project.vilsoncake.Flowt.service.PlaylistChangeService;
import project.vilsoncake.Flowt.service.PlaylistService;

import java.util.Map;

@RestController
@RequestMapping("/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playListService;
    private final PlaylistChangeService playlistChangeService;
    private final LastListenedPlaylistsService lastListenedPlaylistsService;

    @GetMapping("/{username}/{playlistName}")
    public ResponseEntity<PlaylistDto> getPlaylist(
            @PathVariable("username") String username,
            @PathVariable("playlistName") String playlistName
    ) {
        return ResponseEntity.ok(playListService.getPlaylistByNameAndUser(username, playlistName));
    }

    @GetMapping("/info/{username}")
    public ResponseEntity<PlaylistsPageDto> getUserPlaylists(
            @PathVariable("username") String username,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(playListService.getPublicPlaylistsByUser(username, page, size));
    }

    @GetMapping("/last-listened/{username}/{playlistName}")
    public ResponseEntity<Map<String, String>> addPlaylistToLastListened(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @PathVariable("username") String username,
            @PathVariable("playlistName") String playlistName
    ) {
        return ResponseEntity.ok(lastListenedPlaylistsService.addPlaylistToLastListened(authHeader, username, playlistName));
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createNewPlaylist(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @RequestBody PlaylistRequest playlistRequest
    ) {
        return ResponseEntity.ok(playListService.createNewPlaylist(authHeader, playlistRequest));
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

    @PostMapping("/{playlistName}")
    public ResponseEntity<Map<String, String>> addSongsToPlaylist(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @PathVariable("playlistName") String playlistName,
            @RequestBody SongsListDto songsListDto
    ) {
        return ResponseEntity.ok(playListService.addSongsToPlaylist(authHeader, playlistName, songsListDto));
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

    @DeleteMapping("/{playlistName}")
    public ResponseEntity<Map<String, String>> removePlaylist(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @PathVariable("playlistName") String playlistName
    ) {
        return ResponseEntity.ok(playListService.removePlaylist(authHeader, playlistName));
    }
}
