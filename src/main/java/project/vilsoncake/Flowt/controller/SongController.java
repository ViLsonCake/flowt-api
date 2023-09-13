package project.vilsoncake.Flowt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.dto.SongRequest;
import project.vilsoncake.Flowt.dto.SongsResponse;
import project.vilsoncake.Flowt.entity.SongEntity;
import project.vilsoncake.Flowt.exception.InvalidExtensionException;
import project.vilsoncake.Flowt.exception.MinioFileException;
import project.vilsoncake.Flowt.service.SongService;

import java.util.Map;

@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @GetMapping("/{username}/{songName}")
    public ResponseEntity<SongEntity> getSongInfo(
            @PathVariable("username") String username,
            @PathVariable("songName") String songName
    ) {
        return ResponseEntity.ok(songService.getSongInfo(username, songName));
    }

    @GetMapping("/audio/{author}/{songName}")
    public ResponseEntity<byte[]> getSongAudioFile(
            @PathVariable("author") String author,
            @PathVariable("songName") String songName
    ) throws MinioFileException {
        byte[] bytes = songService.getSongAudioFile(author, songName);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("audio/mpeg"))
                .contentLength(bytes.length)
                .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                .header(HttpHeaders.CONTENT_RANGE, String.format("bytes 0-%s/%s", bytes.length - 1, bytes.length))
                .body(bytes);
    }

    @GetMapping("/{genre}")
    public ResponseEntity<SongsResponse> getSongsByGenre(
            @PathVariable("genre") String genre,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(songService.getSongsByGenre(genre, page, size));
    }

    @GetMapping("/random/{genre}")
    public ResponseEntity<SongEntity> getRandomSongByGenre(
            @PathVariable("genre") String genre
    ) {
        return ResponseEntity.ok(songService.getRandomSongInfoByGenre(genre));
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> addNewSongEntity(
            @RequestHeader(value = "Authorization", required = false, defaultValue = "") String authHeader,
            @RequestBody SongRequest songRequest
    ) {
        return ResponseEntity.ok(songService.saveNewSongEntity(authHeader, songRequest));
    }

    @PostMapping("/audio/{name}")
    public ResponseEntity<Map<String, String>> addNewSongFile(
            @RequestHeader(value = "Authorization", required = false, defaultValue = "") String authHeader,
            @PathVariable("name") String name,
            @RequestParam("file") MultipartFile file) throws InvalidExtensionException, MinioFileException {
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

    @DeleteMapping("/{name}")
    public ResponseEntity<Map<String, String>> removeUserSong(
            @RequestHeader(value = "Authorization", required = false, defaultValue = "") String authHeader,
            @PathVariable("name") String name
    ) {
        return ResponseEntity.ok(songService.removeUserSong(authHeader, name));
    }
}
