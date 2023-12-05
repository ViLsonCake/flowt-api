package project.vilsoncake.Flowt.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.dto.*;
import project.vilsoncake.Flowt.entity.NotificationEntity;
import project.vilsoncake.Flowt.entity.PlaylistEntity;
import project.vilsoncake.Flowt.exception.InvalidExtensionException;
import project.vilsoncake.Flowt.exception.MinioFileException;
import project.vilsoncake.Flowt.service.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserManagementService userManagementService;
    private final UserChangeService userChangeService;
    private final UserService userService;
    private final LikedService likedService;
    private final SongService songService;
    private final FollowerService followerService;
    private final SavedPlaylistService savedPlaylistService;

    @PostMapping("/avatar")
    public ResponseEntity<Map<String, String>> addAvatar(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @RequestParam("file") MultipartFile avatar
    ) throws MinioFileException, InvalidExtensionException {
        return ResponseEntity.ok(userManagementService.addUserAvatarByUsername(authHeader, avatar));
    }

    @PostMapping("/avatar/url")
    public ResponseEntity<Map<String, String>> addAvatarFromUrl(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @RequestBody UrlAvatarRequest imageUrl
    ) {
        return ResponseEntity.ok(userManagementService.addUserAvatarUrl(authHeader, imageUrl));
    }

    @PostMapping("/profile-header")
    public ResponseEntity<Map<String, String>> addProfileHeader(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @RequestParam("file") MultipartFile image
    ) throws MinioFileException, InvalidExtensionException {
        return ResponseEntity.ok(userManagementService.addUserProfileHeaderByUsername(authHeader, image));
    }

    @PostMapping("/change-password")
    public ResponseEntity<Map<String, String>> changePassword(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @RequestBody ChangePasswordDto changePasswordDto
            ) {
        return ResponseEntity.ok(userService.changeUserPasswordByUsername(authHeader, changePasswordDto));
    }

    @PostMapping("/restore-password")
    public ResponseEntity<Map<String, String>> restorePassword(@RequestBody RestorePasswordDto restorePasswordDto) {
        return ResponseEntity.ok(userService.restorePassword(restorePasswordDto));
    }

    @GetMapping("/authenticated")
    public ResponseEntity<UserDto> getAuthenticatedUserDto(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        return ResponseEntity.ok(userService.getAuthenticatedUserDto(authHeader));
    }

    @PostMapping("/subscribe/{username}")
    public ResponseEntity<Map<String, String>> subscribeToUser(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @PathVariable("username") String username
    ) {
        return ResponseEntity.ok(userManagementService.subscribeToUser(authHeader, username));
    }

    @PostMapping("/unsubscribe/{username}")
    public ResponseEntity<Map<String, String>> unsubscribeToUser(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @PathVariable("username") String username
    ) {
        return ResponseEntity.ok(userManagementService.unsubscribeToUser(authHeader, username));
    }

    @GetMapping("/subscribes")
    public ResponseEntity<SubscribesDto> getUserSubscribes(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(followerService.getUserSubscribes(authHeader, page, size));
    }

    @GetMapping("/followers")
    public ResponseEntity<FollowersDto> getUserFollowers(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(followerService.getUserFollowers(authHeader, page, size));
    }

    @GetMapping("/liked")
    public ResponseEntity<LikedSongsDto> getLikedSongs(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(likedService.getLikedSongs(authHeader, page, size));
    }

    @GetMapping("/saved-playlists")
    public ResponseEntity<SavedPlaylistsDto> getSavedPlaylists(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(savedPlaylistService.getUserSavedPlaylists(authHeader, page, size));
    }

    @GetMapping("/songs")
    public ResponseEntity<SongsResponse> getUserSongs(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(songService.getAuthenticatedUserSongs(authHeader, page, size));
    }

    @GetMapping("/playlists")
    public ResponseEntity<Map<String, List<PlaylistEntity>>> getUserPlaylists(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        return ResponseEntity.ok(userService.getUserPlaylists(authHeader));
    }

    @GetMapping("/notifications")
    public ResponseEntity<Map<String, List<NotificationEntity>>> getUserNotifications(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        return ResponseEntity.ok(userService.getUserNotifications(authHeader));
    }

    @GetMapping("/last-listened/songs")
    public ResponseEntity<LastListenedSongsDto> getUserLastListened(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        return ResponseEntity.ok(userService.getLastListenedSongs(authHeader));
    }

    @GetMapping("/last-listened/playlists")
    public ResponseEntity<LastListenedPlaylistsDto> getLastListenedPlaylists(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        return ResponseEntity.ok(userService.getLastListenedPlaylists(authHeader));
    }

    @PatchMapping("/username")
    public ResponseEntity<JwtResponse> changeUsername(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @RequestBody UsernameDto usernameDto,
            HttpServletResponse response
            ) {
        return ResponseEntity.ok(userChangeService.changeUserUsername(authHeader, usernameDto, response));
    }

    @PatchMapping("/email")
    public ResponseEntity<Map<String, String>> changeEmail(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @RequestBody EmailDto emailDto
    ) {
        return ResponseEntity.ok(userChangeService.changeUserEmail(authHeader, emailDto));
    }

    @PatchMapping("/region")
    public ResponseEntity<Map<String, String>> changeRegion(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @RequestBody RegionDto regionDto
    ) {
        return ResponseEntity.ok(userChangeService.changeUserRegion(authHeader, regionDto));
    }

    @PatchMapping("/description")
    public ResponseEntity<Map<String, String>> changeDescription(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @RequestBody DescriptionDto descriptionDto
    ) {
        return ResponseEntity.ok(userChangeService.changeUserDescription(authHeader, descriptionDto));
    }
}
