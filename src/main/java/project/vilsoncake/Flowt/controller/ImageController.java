package project.vilsoncake.Flowt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.vilsoncake.Flowt.exception.MinioFileException;
import project.vilsoncake.Flowt.service.PlaylistService;
import project.vilsoncake.Flowt.service.SongService;
import project.vilsoncake.Flowt.service.UserManagementService;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final UserManagementService userManagementService;
    private final SongService songService;
    private final PlaylistService playlistService;

    @GetMapping("/user/avatar/{username}")
    public ResponseEntity<byte[]> getUserAvatar(@PathVariable("username") String username) throws MinioFileException {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(userManagementService.getUserAvatarByUsername(username));
    }

    @GetMapping("/user/profile-hat/{username}")
    public ResponseEntity<byte[]> getUserProfileHat(@PathVariable("username") String username) throws MinioFileException {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(userManagementService.getUserProfileHatByUsername(username));
    }

    @GetMapping("/song/{username}/{songName}")
    public ResponseEntity<byte[]> getSongAvatar(
            @PathVariable("username") String username,
            @PathVariable("songName") String songName
    ) throws MinioFileException {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(songService.getSongAvatarBySongName(username, songName));
    }

    @GetMapping("/playlist/{username}/{playlistName}")
    public ResponseEntity<byte[]> getPlaylistAvatar(
            @PathVariable("username") String username,
            @PathVariable("playlistName") String playlistName
    ) throws MinioFileException {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(playlistService.getPlaylistAvatar(username, playlistName));
    }
}
