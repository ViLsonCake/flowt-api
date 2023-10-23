package project.vilsoncake.Flowt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.vilsoncake.Flowt.dto.ArtistVerifyDto;
import project.vilsoncake.Flowt.dto.PasswordCodeDto;
import project.vilsoncake.Flowt.exception.AccountAlreadyVerifiedException;
import project.vilsoncake.Flowt.exception.VerifyCodeNotFoundException;
import project.vilsoncake.Flowt.service.ArtistVerifyService;
import project.vilsoncake.Flowt.service.UserVerifyService;

import java.util.Map;

@RestController
@RequestMapping("/verify")
@RequiredArgsConstructor
public class VerifyController {

    private final UserVerifyService userVerifyService;
    private final ArtistVerifyService artistVerifyService;

    @GetMapping("/email")
    public ResponseEntity<Map<String, String>> verifyUserEmail(@RequestParam("code") String code) throws AccountAlreadyVerifiedException, VerifyCodeNotFoundException {
        return ResponseEntity.ok(userVerifyService.verifyUserEmail(code));
    }

    @GetMapping("/password")
    public ResponseEntity<Map<String, String>> changePassword(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        return ResponseEntity.ok(userVerifyService.sendChangePasswordMessageByUsername(authHeader));
    }

    @PostMapping("/artist")
    public ResponseEntity<Map<String, String>> sendVerifyArtistRequest(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @RequestBody ArtistVerifyDto artistVerifyDto
            ) {
        return ResponseEntity.ok(artistVerifyService.saveNewArtistVerifyRequest(authHeader, artistVerifyDto));
    }

    @PostMapping("/restore-password")
    public ResponseEntity<Map<String, String>> restorePassword(@RequestBody PasswordCodeDto restorePasswordDto) {
        return ResponseEntity.ok(userVerifyService.sendChangePasswordMessageByEmail(restorePasswordDto.getEmail()));
    }
}
