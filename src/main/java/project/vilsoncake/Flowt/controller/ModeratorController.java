package project.vilsoncake.Flowt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.vilsoncake.Flowt.service.UserVerifyService;

import java.util.Map;

@RestController
@RequestMapping("/moderator")
@RequiredArgsConstructor
public class ModeratorController {

    private final UserVerifyService userVerifyService;

    @PostMapping("/warning-mail/{username}")
    public ResponseEntity<Map<String, String>> sendWarningMail(@PathVariable("username") String username) {
        return ResponseEntity.ok(userVerifyService.sendWarningMessage(username));
    }
}
