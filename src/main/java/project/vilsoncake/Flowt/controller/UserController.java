package project.vilsoncake.Flowt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.dto.ChangePasswordDto;
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
}
