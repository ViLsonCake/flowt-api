package project.vilsoncake.Flowt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.dto.ChangePasswordDto;
import project.vilsoncake.Flowt.dto.RestorePasswordDto;
import project.vilsoncake.Flowt.dto.UserDto;
import project.vilsoncake.Flowt.dto.UsernameDto;
import project.vilsoncake.Flowt.exception.InvalidExtensionException;
import project.vilsoncake.Flowt.exception.MinioFileException;
import project.vilsoncake.Flowt.service.UserManagementService;
import project.vilsoncake.Flowt.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserManagementService userManagementService;
    private final UserService userService;

    @PostMapping("/avatar")
    public ResponseEntity<Boolean> addAvatar(
            @RequestHeader(value = "Authorization", required = false, defaultValue = "") String authHeader,
            @RequestParam("file") MultipartFile avatar
    ) throws MinioFileException, InvalidExtensionException {
        return ResponseEntity.ok(userManagementService.addUserAvatarByUsername(authHeader, avatar));
    }

    @PostMapping("/change-password")
    public ResponseEntity<Boolean> changePassword(
            @RequestHeader(value = "Authorization", required = false, defaultValue = "") String authHeader,
            @RequestBody ChangePasswordDto changePasswordDto
            ) {
        return ResponseEntity.ok(userService.changeUserPasswordByUsername(authHeader, changePasswordDto));
    }

    @PostMapping("/restore-password")
    public ResponseEntity<Boolean> restorePassword(@RequestBody RestorePasswordDto restorePasswordDto) {
        return ResponseEntity.ok(userService.restorePassword(restorePasswordDto));
    }

    @GetMapping("/authenticated")
    public ResponseEntity<UserDto> getAuthenticatedUserDto(@RequestHeader(value = "Authorization", required = false, defaultValue = "") String authHeader) {
        return ResponseEntity.ok(userService.getAuthenticatedUserDto(authHeader));
    }

    @GetMapping("/username")
    public ResponseEntity<UsernameDto> getAuthenticatedUserUsername(@RequestHeader(value = "Authorization", required = false, defaultValue = "") String authHeader) {
        return ResponseEntity.ok(new UsernameDto(userService.getAuthenticatedUserDto(authHeader).getUsername()));
    }

    @GetMapping("/subscribe/{username}")
    public ResponseEntity<?> subscribeToUser(
            @RequestHeader(value = "Authorization", required = false, defaultValue = "") String authHeader,
            @PathVariable("username") String username) {
        return ResponseEntity.ok(userManagementService.subscribeToUser(authHeader, username));
    }
}
