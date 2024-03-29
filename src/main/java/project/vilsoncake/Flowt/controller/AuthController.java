package project.vilsoncake.Flowt.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.vilsoncake.Flowt.dto.*;
import project.vilsoncake.Flowt.service.AuthService;
import project.vilsoncake.Flowt.service.UserService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<Map<String, String>> registrationUser(@RequestBody RegistrationDto registrationDto) throws IOException {
        return ResponseEntity.ok(userService.addUser(registrationDto));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody JwtRequest authRequest, HttpServletResponse response) {
        return ResponseEntity.ok(authService.generateAuthTokensAndSave(authRequest, response));
    }

    @GetMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshUserToken(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authRequest,
            @CookieValue("refreshToken") String refreshToken,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok(authService.refreshTokens(authRequest, refreshToken, response));
    }

    @PostMapping("/oauth/google")
    public ResponseEntity<JwtResponse> googleOauthLogin(@RequestBody GoogleOauthRequest googleOauthRequest, HttpServletResponse response) throws URISyntaxException {
        return ResponseEntity.ok(authService.getAuthTokenByGoogleAuthorizationCode(googleOauthRequest, response));
    }

    @PostMapping("/oauth/facebook")
    public ResponseEntity<JwtResponse> facebookOauthLogin(@RequestBody FacebookOauthRequest facebookOauthRequest, HttpServletResponse response) throws URISyntaxException {
        return ResponseEntity.ok(authService.getJwtFromFacebookAccessToken(facebookOauthRequest, response));
    }

}
