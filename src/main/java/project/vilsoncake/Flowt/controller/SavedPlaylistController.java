package project.vilsoncake.Flowt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.vilsoncake.Flowt.dto.SavePlaylistDto;
import project.vilsoncake.Flowt.service.SavedPlaylistService;

import java.util.Map;

@RestController
@RequestMapping("/saved-playlists")
@RequiredArgsConstructor
public class SavedPlaylistController {

    private final SavedPlaylistService savedPlaylistService;

    @PostMapping
    public ResponseEntity<Map<String, String>> addPlaylistToSaved(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @RequestBody SavePlaylistDto savePlaylistDto
    ) {
        return ResponseEntity.ok(savedPlaylistService.addPlaylistToSaved(authHeader, savePlaylistDto));
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> removePlaylistFromSaved(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @RequestBody SavePlaylistDto savePlaylistDto
    ) {
        return ResponseEntity.ok(savedPlaylistService.removePlaylistFromSaved(authHeader, savePlaylistDto));
    }
}
