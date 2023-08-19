package project.vilsoncake.Flowt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.vilsoncake.Flowt.exception.MinioFileException;
import project.vilsoncake.Flowt.service.UserManagementService;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final UserManagementService userManagementService;

    @GetMapping("/user/{username}")
    public ResponseEntity<byte[]> getUserAvatar(@PathVariable String username) throws MinioFileException {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(userManagementService.getUserAvatarByUsername(username));
    }
}
