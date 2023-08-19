package project.vilsoncake.Flowt.service.impl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.vilsoncake.Flowt.config.JwtConfig;
import project.vilsoncake.Flowt.entity.TokenEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.repository.TokenRepository;
import project.vilsoncake.Flowt.service.TokenService;
import project.vilsoncake.Flowt.service.UserService;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;
    private final UserService userService;
    private final JwtConfig jwtConfig;

    @Override
    public void saveNewToken(String token, String username, HttpServletResponse response) {
        UserEntity user = userService.getUserByUsername(username);
        TokenEntity tokenFromDb = tokenRepository.findByUser(user);
        if (tokenFromDb != null) tokenRepository.delete(tokenFromDb);

        // Save refresh token to db
        TokenEntity tokenEntity = new TokenEntity(token, user);
        tokenRepository.save(tokenEntity);

        // Add refresh token to http-only cookie
        Cookie tokenCookie = new Cookie("refreshToken", token);
        tokenCookie.setMaxAge(daysToSeconds(jwtConfig.getRefreshLifetime()));
        tokenCookie.setHttpOnly(true);
        response.addCookie(tokenCookie);
    }

    @Override
    public boolean isTokenExists(String token) {
        return tokenRepository.existsByToken(token);
    }

    private Integer daysToSeconds(Integer days) {
        return days * 24 * 60 * 60;
    }
}