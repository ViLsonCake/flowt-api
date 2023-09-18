package project.vilsoncake.Flowt.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.vilsoncake.Flowt.dto.JwtRequest;
import project.vilsoncake.Flowt.dto.JwtResponse;
import project.vilsoncake.Flowt.dto.RegistrationDto;
import project.vilsoncake.Flowt.service.AuthService;
import project.vilsoncake.Flowt.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<Map<String, String>> registrationUser(@RequestBody RegistrationDto registrationDto) {
        return ResponseEntity.ok(userService.addUser(registrationDto));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody JwtRequest authRequest, HttpServletResponse response) {
        return ResponseEntity.ok(authService.generateAuthTokensAndSave(authRequest, response));
    }

    @GetMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshUserToken(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authRequest, HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(authService.refreshTokens(authRequest, request, response));
    }
}
