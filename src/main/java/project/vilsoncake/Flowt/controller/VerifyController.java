package project.vilsoncake.Flowt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.vilsoncake.Flowt.dto.PasswordCodeDto;
import project.vilsoncake.Flowt.dto.RestorePasswordResponse;
import project.vilsoncake.Flowt.exception.AccountAlreadyVerifiedException;
import project.vilsoncake.Flowt.exception.VerifyCodeNotFoundException;
import project.vilsoncake.Flowt.service.UserVerifyService;

import java.util.Map;

@RestController
@RequestMapping("/verify")
@RequiredArgsConstructor
public class VerifyController {

    private final UserVerifyService userVerifyService;

    @GetMapping("/email")
    public ResponseEntity<Map<String, String>> verifyEmail(@RequestParam("code") String code) throws AccountAlreadyVerifiedException, VerifyCodeNotFoundException {
        return ResponseEntity.ok(userVerifyService.verifyUser(code));
    }

    @GetMapping("/password")
    public ResponseEntity<Map<String, String>> changePassword(@RequestHeader(value = "Authorization", required = false, defaultValue = "") String authHeader) {
        return ResponseEntity.ok(userVerifyService.sendChangePasswordMessageByUsername(authHeader));
    }

    @PostMapping("/restore-password")
    public ResponseEntity<Map<String, String>> restorePassword(@RequestBody PasswordCodeDto restorePasswordDto) {
        return ResponseEntity.ok(userVerifyService.sendChangePasswordMessageByEmail(restorePasswordDto.getEmail()));
    }
}
