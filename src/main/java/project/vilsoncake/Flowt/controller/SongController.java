package project.vilsoncake.Flowt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.dto.SongDto;
import project.vilsoncake.Flowt.exception.InvalidExtensionException;
import project.vilsoncake.Flowt.service.SongService;

import java.util.Map;

@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @PostMapping
    public ResponseEntity<Map<String, String>> addNewSongEntity(
            @RequestHeader(value = "Authorization", required = false, defaultValue = "") String authHeader,
            @RequestBody SongDto songDto
    ) {
        return ResponseEntity.ok(songService.saveNewSongEntity(authHeader, songDto));
    }

    @PostMapping("/audio/{name}")
    public ResponseEntity<Map<String, String>> addNewSongFile(
            @RequestHeader(value = "Authorization", required = false, defaultValue = "") String authHeader,
            @PathVariable("name") String name,
            @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(songService.saveNewAudioFile(authHeader, name, file));
    }

    @PostMapping("/avatar/{name}")
    public ResponseEntity<Map<String, String>> changeSongAvatar(
            @RequestHeader(value = "Authorization", required = false, defaultValue = "") String authHeader,
            @PathVariable("name") String name,
            @RequestParam("file") MultipartFile avatar
    ) throws InvalidExtensionException {
        return ResponseEntity.ok(songService.addAvatarByUserSongName(authHeader, name, avatar));
    }
}
