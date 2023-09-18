package project.vilsoncake.Flowt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.vilsoncake.Flowt.service.LikedService;

import java.util.Map;

@RestController
@RequestMapping("/liked")
@RequiredArgsConstructor
public class LikedController {

    private final LikedService likedService;

    @PostMapping("/{username}/{songName}")
    public ResponseEntity<Map<String, String>> addSongToLiked(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @PathVariable("username") String username,
            @PathVariable("songName") String songName
    ) {
        return ResponseEntity.ok(likedService.addSongToLiked(authHeader, username, songName));
    }

    @DeleteMapping("/{username}/{songName}")
    public ResponseEntity<Map<String, String>> removeSongFromLiked(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @PathVariable("username") String username,
            @PathVariable("songName") String songName
    ) {
        return ResponseEntity.ok(likedService.removeSongFromLiked(authHeader, username, songName));
    }
}
