package project.vilsoncake.Flowt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.vilsoncake.Flowt.dto.ArtistVerifyPageDto;
import project.vilsoncake.Flowt.service.ArtistVerifyService;
import project.vilsoncake.Flowt.service.UserChangeService;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/moderator")
@RequiredArgsConstructor
public class ModeratorController {

    private final UserChangeService userChangeService;
    private final ArtistVerifyService artistVerifyService;

    @PatchMapping("/active/{username}")
    public ResponseEntity<Map<String, Boolean>> changeActive(@PathVariable("username") String username) {
        return ResponseEntity.ok(userChangeService.changeUserActive(username));
    }

    @GetMapping("/artist/requests")
    public ResponseEntity<ArtistVerifyPageDto> getVerifyRequests(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(artistVerifyService.getArtistVerifyRequestsByLatestDate(page, size));
    }

    @PatchMapping("/artist/{username}")
    public ResponseEntity<Map<String, String>> verifyArtist(@PathVariable("username") String username) throws IOException {
        return ResponseEntity.ok(artistVerifyService.verifyArtistByUsername(username));
    }

    @DeleteMapping("/artist/{username}")
    public ResponseEntity<Map<String, String>> cancelVerifyArtistRequest(@PathVariable("username") String username) {
        return ResponseEntity.ok(artistVerifyService.cancelVerifyRequestByUsername(username));
    }
}
