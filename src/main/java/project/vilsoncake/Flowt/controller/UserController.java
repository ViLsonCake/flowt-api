package project.vilsoncake.Flowt.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.dto.*;
import project.vilsoncake.Flowt.exception.InvalidExtensionException;
import project.vilsoncake.Flowt.exception.MinioFileException;
import project.vilsoncake.Flowt.service.ChangeUserService;
import project.vilsoncake.Flowt.service.LikedService;
import project.vilsoncake.Flowt.service.UserManagementService;
import project.vilsoncake.Flowt.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserManagementService userManagementService;
    private final ChangeUserService changeUserService;
    private final UserService userService;
    private final LikedService likedService;

    @PostMapping("/avatar")
    public ResponseEntity<Map<String, String>> addAvatar(
            @RequestHeader(value = "Authorization", required = false, defaultValue = "") String authHeader,
            @RequestParam("file") MultipartFile avatar
    ) throws MinioFileException, InvalidExtensionException {
        return ResponseEntity.ok(userManagementService.addUserAvatarByUsername(authHeader, avatar));
    }

    @PostMapping("/change-password")
    public ResponseEntity<Map<String, String>> changePassword(
            @RequestHeader(value = "Authorization", required = false, defaultValue = "") String authHeader,
            @RequestBody ChangePasswordDto changePasswordDto
            ) {
        return ResponseEntity.ok(userService.changeUserPasswordByUsername(authHeader, changePasswordDto));
    }

    @PostMapping("/restore-password")
    public ResponseEntity<Map<String, String>> restorePassword(@RequestBody RestorePasswordDto restorePasswordDto) {
        return ResponseEntity.ok(userService.restorePassword(restorePasswordDto));
    }

    @GetMapping("/authenticated")
    public ResponseEntity<UserDto> getAuthenticatedUserDto(@RequestHeader(value = "Authorization", required = false, defaultValue = "") String authHeader) {
        return ResponseEntity.ok(userService.getAuthenticatedUserDto(authHeader));
    }

    @PostMapping("/subscribe/{username}")
    public ResponseEntity<Map<String, String>> subscribeToUser(
            @RequestHeader(value = "Authorization", required = false, defaultValue = "") String authHeader,
            @PathVariable("username") String username
    ) {
        return ResponseEntity.ok(userManagementService.subscribeToUser(authHeader, username));
    }

    @PostMapping("/unsubscribe/{username}")
    public ResponseEntity<Map<String, String>> unsubscribeToUser(
            @RequestHeader(value = "Authorization", required = false, defaultValue = "") String authHeader,
            @PathVariable("username") String username
    ) {
        return ResponseEntity.ok(userManagementService.unsubscribeToUser(authHeader, username));
    }

    @GetMapping("/subscribes")
    public ResponseEntity<Map<String, List<String>>> getUserSubscribes(@RequestHeader(value = "Authorization", required = false, defaultValue = "") String authHeader) {
        return ResponseEntity.ok(userService.getAllUserSubscribesUsernames(authHeader));
    }

    @GetMapping("/followers")
    public ResponseEntity<Map<String, List<String>>> getUserFollowers(@RequestHeader(value = "Authorization", required = false, defaultValue = "") String authHeader) {
        return ResponseEntity.ok(userService.getAllUserFollowersUsernames(authHeader));
    }

    @GetMapping("/liked")
    public ResponseEntity<Map<String, Map<String, String>>> getLikedSongs(@RequestHeader(value = "Authorization", required = false, defaultValue = "") String authHeader) {
        return ResponseEntity.ok(likedService.getLikedSongs(authHeader));
    }

    @GetMapping("/songs")
    public ResponseEntity<Map<String, List<String>>> getUserSongs(@RequestHeader(value = "Authorization", required = false, defaultValue = "") String authHeader) {
        return ResponseEntity.ok(userService.getUserSongs(authHeader));
    }

    @PatchMapping("/username")
    public ResponseEntity<Map<String, String>> changeUsername(
            @RequestHeader(value = "Authorization", required = false, defaultValue = "") String authHeader,
            @RequestBody UsernameDto usernameDto,
            HttpServletResponse response
            ) {
        return ResponseEntity.ok(changeUserService.changeUserUsername(authHeader, usernameDto, response));
    }

    @PatchMapping("/email")
    public ResponseEntity<Map<String, String>> changeEmail(
            @RequestHeader(value = "Authorization", required = false, defaultValue = "") String authHeader,
            @RequestBody EmailDto emailDto
    ) {
        return ResponseEntity.ok(changeUserService.changeUserEmail(authHeader, emailDto));
    }

    @PatchMapping("/region")
    public ResponseEntity<Map<String, String>> changeRegion(
            @RequestHeader(value = "Authorization", required = false, defaultValue = "") String authHeader,
            @RequestBody RegionDto regionDto
    ) {
        return ResponseEntity.ok(changeUserService.changeUserRegion(authHeader, regionDto));
    }

    @PatchMapping("/description")
    public ResponseEntity<Map<String, String>> changeDescription(
            @RequestHeader(value = "Authorization", required = false, defaultValue = "") String authHeader,
            @RequestBody DescriptionDto descriptionDto
    ) {
        return ResponseEntity.ok(changeUserService.changeUserDescription(authHeader, descriptionDto));
    }
}
