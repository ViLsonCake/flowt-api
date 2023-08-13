package project.vilsoncake.Flowt.service;

import jakarta.servlet.http.HttpServletResponse;

public interface TokenService {
    void saveNewToken(String token, String username, HttpServletResponse response);
    boolean isTokenExists(String token);
}
