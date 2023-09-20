package project.vilsoncake.Flowt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.vilsoncake.Flowt.service.NotificationService;

import java.util.Map;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteUserNotification(@PathVariable("id") Long id) {
        return ResponseEntity.ok(notificationService.removeNotificationById(id));
    }

}
