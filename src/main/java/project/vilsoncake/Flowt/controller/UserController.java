package project.vilsoncake.Flowt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.exception.InvalidExtensionException;
import project.vilsoncake.Flowt.exception.MinioFileException;
import project.vilsoncake.Flowt.service.UserManagementService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserManagementService userManagementService;

    @PostMapping("/avatar")
    public ResponseEntity<String> addAvatar(
            @RequestHeader(value = "Authorization", required = false, defaultValue = "") String authHeader,
            @RequestParam("file") MultipartFile avatar
    ) throws MinioFileException, InvalidExtensionException {
        userManagementService.addUserAvatarByUsername(authHeader, avatar);
        return ResponseEntity.ok("Success!");
    }
}
