package project.vilsoncake.Flowt.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import project.vilsoncake.Flowt.dto.GoogleOauthRequest;
import project.vilsoncake.Flowt.dto.JwtRequest;
import project.vilsoncake.Flowt.dto.JwtResponse;

import java.net.URISyntaxException;

public interface AuthService {
    JwtResponse generateAuthTokensAndSave(JwtRequest authRequest, HttpServletResponse response);
    JwtResponse refreshTokens(String authRequest, String cookieRefreshToken, HttpServletResponse response);
    JwtResponse getAuthTokenByGoogleAuthorizationCode(GoogleOauthRequest googleOauthRequest, HttpServletResponse response) throws URISyntaxException;
}
