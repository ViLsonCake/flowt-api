package project.vilsoncake.Flowt.service.impl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.vilsoncake.Flowt.dto.JwtRequest;
import project.vilsoncake.Flowt.dto.JwtResponse;
import project.vilsoncake.Flowt.exception.IncorrectCredentialsException;
import project.vilsoncake.Flowt.exception.TokenNotFoundException;
import project.vilsoncake.Flowt.service.AuthService;
import project.vilsoncake.Flowt.service.TokenService;
import project.vilsoncake.Flowt.utils.AuthUtils;
import project.vilsoncake.Flowt.utils.JwtUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final TokenService tokenService;
    private final JwtUtils jwtUtils;
    private final AuthUtils authUtils;

    @Override
    public JwtResponse generateAuthTokensAndSave(JwtRequest authRequest, HttpServletResponse response) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new IncorrectCredentialsException("Incorrect login or password");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

        if (!userDetails.isEnabled()) throw new IncorrectCredentialsException("Your account has been blocked");

        String[] tokens = jwtUtils.generateTokens(userDetails);
        tokenService.saveNewToken(tokens[1], authRequest.getUsername(), response);
        return new JwtResponse(tokens[0]);
    }

    @Transactional
    @Override
    public JwtResponse refreshTokens(String authRequest, HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = getRefreshFromCookie(request.getCookies());
        String usernameFromRefresh = getUsernameFromRefresh(refreshToken);
        String usernameFromAccess = authUtils.getUsernameFromAuthHeader(authRequest);

        if (!usernameFromAccess.equals(usernameFromRefresh)) throw new TokenNotFoundException("Token not valid");

        UserDetails user = userDetailsService.loadUserByUsername(usernameFromRefresh);
        String[] tokens = jwtUtils.generateTokens(user);
        tokenService.saveNewToken(tokens[1], usernameFromRefresh, response);
        log.info("New token: {}", tokens[0]);
        return new JwtResponse(tokens[0]);
    }

    private String getRefreshFromCookie(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refreshToken"))
                return cookie.getValue();
        }
        return null;
    }

    private String getUsernameFromRefresh(String refreshToken) {
        if (!tokenService.isTokenExists(refreshToken)) {
            log.warn("Token not found");
            throw new TokenNotFoundException("Token not found");
        }
        try {
            return jwtUtils.getUsernameFromRefresh(refreshToken);
        } catch (ExpiredJwtException e) {
            log.warn("Token time is expired :(");
            throw new TokenNotFoundException("Token time is expired :(");
        } catch (SignatureException | MalformedJwtException e) {
            log.warn("Invalid token signature");
            throw new TokenNotFoundException("Invalid token signature");
        }
    }
}
