package project.vilsoncake.Flowt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.vilsoncake.Flowt.dto.RegistrationDto;
import project.vilsoncake.Flowt.service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<String> registrationUser(@RequestBody RegistrationDto registrationDto) {
        return ResponseEntity.ok(String.format("User '%s' saved", userService.addUser(registrationDto).getUsername()));
    }
}
