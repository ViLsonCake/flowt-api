package project.vilsoncake.Flowt.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.property.JwtProperties;
import project.vilsoncake.Flowt.repository.TokenRepository;
import project.vilsoncake.Flowt.service.TokenService;
import project.vilsoncake.Flowt.service.UserService;

import java.time.Duration;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;
    private final UserService userService;
    private final JwtProperties jwtProperties;

    @Transactional
    @Override
    public void saveNewToken(String token, String username, HttpServletResponse response) {
        UserEntity user = userService.getUserByUsername(username);
        user.getToken().setToken(token);

        ResponseCookie responseCookie = ResponseCookie.from("refreshToken", token)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/auth")
                .maxAge(Duration.ofDays(jwtProperties.getRefreshLifetime()))
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
    }

    @Override
    public boolean isTokenExists(String token) {
        return tokenRepository.existsByToken(token);
    }
}
