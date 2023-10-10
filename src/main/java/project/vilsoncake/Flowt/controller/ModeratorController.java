package project.vilsoncake.Flowt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.vilsoncake.Flowt.service.UserChangeService;

import java.util.Map;

@RestController
@RequestMapping("/moderator")
@RequiredArgsConstructor
public class ModeratorController {

    private final UserChangeService userChangeService;

    @PatchMapping("/active/{username}")
    public ResponseEntity<Map<String, Boolean>> changeActive(@PathVariable("username") String username) {
        return ResponseEntity.ok(userChangeService.changeUserActive(username));
    }
}
