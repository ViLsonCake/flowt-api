package project.vilsoncake.Flowt.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import project.vilsoncake.Flowt.dto.JwtRequest;
import project.vilsoncake.Flowt.dto.JwtResponse;

public interface AuthService {
    JwtResponse generateAuthTokensAndSave(JwtRequest authRequest, HttpServletResponse response);
    JwtResponse refreshTokens(String authRequest, HttpServletRequest request, HttpServletResponse response);
}
