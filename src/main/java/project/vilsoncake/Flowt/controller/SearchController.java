package project.vilsoncake.Flowt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.vilsoncake.Flowt.dto.PlaylistsPageDto;
import project.vilsoncake.Flowt.dto.SongsResponse;
import project.vilsoncake.Flowt.dto.SubstringDto;
import project.vilsoncake.Flowt.dto.UsersPageDto;
import project.vilsoncake.Flowt.service.PlaylistService;
import project.vilsoncake.Flowt.service.SongService;
import project.vilsoncake.Flowt.service.UserService;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SongService songService;
    private final PlaylistService playlistService;
    private final UserService userService;

    @GetMapping("/songs")
    public ResponseEntity<SongsResponse> searchSongsBySubstring(
            @RequestParam String substring,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(songService.getSongsBySubstring(substring, page, size));
    }

    @GetMapping("/playlists")
    public ResponseEntity<PlaylistsPageDto> searchPlaylistsBySubstring(
            @RequestParam String substring,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(playlistService.getPublicPlaylistsBySubstring(substring, page, size));
    }

    @GetMapping("/users")
    public ResponseEntity<UsersPageDto> searchUsersBySubstring(
            @RequestParam String substring,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(userService.getUsersDtoBySubstring(substring, page, size));
    }
}
