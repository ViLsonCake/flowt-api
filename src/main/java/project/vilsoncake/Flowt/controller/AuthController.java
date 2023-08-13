package project.vilsoncake.Flowt.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.vilsoncake.Flowt.dto.JwtRequest;
import project.vilsoncake.Flowt.dto.JwtResponse;
import project.vilsoncake.Flowt.dto.RegistrationDto;
import project.vilsoncake.Flowt.service.AuthService;
import project.vilsoncake.Flowt.service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<String> registrationUser(@RequestBody RegistrationDto registrationDto) {
        return ResponseEntity.ok(String.format("User '%s' saved", userService.addUser(registrationDto).getUsername()));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody JwtRequest authRequest, HttpServletResponse response) {
        return ResponseEntity.ok(authService.generateAuthTokensAndSave(authRequest, response));
    }
}
