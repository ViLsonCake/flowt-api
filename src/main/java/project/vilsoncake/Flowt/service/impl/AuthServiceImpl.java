package project.vilsoncake.Flowt.service.impl;

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
import project.vilsoncake.Flowt.dto.JwtRequest;
import project.vilsoncake.Flowt.dto.JwtResponse;
import project.vilsoncake.Flowt.exception.IncorrectCredentialsException;
import project.vilsoncake.Flowt.service.AuthService;
import project.vilsoncake.Flowt.service.TokenService;
import project.vilsoncake.Flowt.utils.JwtUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final TokenService tokenService;


    @Override
    public JwtResponse generateAuthTokensAndSave(JwtRequest authRequest, HttpServletResponse response) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new IncorrectCredentialsException("Incorrect login or password");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        String[] tokens = jwtUtils.generateTokens(userDetails);
        tokenService.saveNewToken(tokens[1], authRequest.getUsername(), response);
        return new JwtResponse(tokens[0]);
    }

    @Override
    public JwtResponse refreshTokens(String authRequest, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
