package project.vilsoncake.Flowt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.vilsoncake.Flowt.service.ChangeUserService;
import project.vilsoncake.Flowt.service.UserVerifyService;

import java.util.Map;

@RestController
@RequestMapping("/moderator")
@RequiredArgsConstructor
public class ModeratorController {

    private final UserVerifyService userVerifyService;
    private final ChangeUserService changeUserService;

    @PatchMapping("/active/{username}")
    public ResponseEntity<Map<String, Boolean>> changeActive(@PathVariable("username") String username) {
        return ResponseEntity.ok(changeUserService.changeUserActive(username));
    }

    @PostMapping("/warning-mail/{username}")
    public ResponseEntity<Map<String, String>> sendWarningMail(@PathVariable("username") String username) {
        return ResponseEntity.ok(userVerifyService.sendWarningMessage(username));
    }
}
