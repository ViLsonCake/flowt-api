package project.vilsoncake.Flowt.service.impl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import project.vilsoncake.Flowt.dto.GoogleAccessTokenResponse;
import project.vilsoncake.Flowt.dto.GoogleOauthRequest;
import project.vilsoncake.Flowt.dto.JwtRequest;
import project.vilsoncake.Flowt.dto.JwtResponse;
import project.vilsoncake.Flowt.exception.IncorrectCredentialsException;
import project.vilsoncake.Flowt.exception.TokenNotFoundException;
import project.vilsoncake.Flowt.properties.GoogleOauthProperties;
import project.vilsoncake.Flowt.service.AuthService;
import project.vilsoncake.Flowt.service.TokenService;
import project.vilsoncake.Flowt.utils.AuthUtils;
import project.vilsoncake.Flowt.utils.JwtUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final TokenService tokenService;
    private final JwtUtils jwtUtils;
    private final AuthUtils authUtils;
    private final GoogleOauthProperties googleOauthProperties;

    @Override
    public JwtResponse generateAuthTokensAndSave(JwtRequest authRequest, HttpServletResponse response) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getLogin());

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDetails.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new IncorrectCredentialsException("Incorrect login or password");
        }
        if (!userDetails.isEnabled()) {
            throw new IncorrectCredentialsException("Your account has been blocked");
        }

        String[] tokens = jwtUtils.generateTokens(userDetails);
        tokenService.saveNewToken(tokens[1], userDetails.getUsername(), response);
        return new JwtResponse(tokens[0]);
    }

    @Transactional
    @Override
    public JwtResponse refreshTokens(String authRequest, HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = getRefreshFromCookie(request.getCookies());
        String usernameFromRefresh = getUsernameFromRefresh(refreshToken);
        String usernameFromAccess = authUtils.getUsernameFromAuthHeader(authRequest);

        if (!usernameFromAccess.equals(usernameFromRefresh)) {
            throw new TokenNotFoundException("Token not valid");
        }

        UserDetails user = userDetailsService.loadUserByUsername(usernameFromRefresh);
        String[] tokens = jwtUtils.generateTokens(user);
        tokenService.saveNewToken(tokens[1], usernameFromRefresh, response);
        log.info("New token: {}", tokens[0]);
        return new JwtResponse(tokens[0]);
    }

    @Override
    public JwtResponse getAuthTokenByGoogleAuthorizationCode(GoogleOauthRequest googleOauthRequest, HttpServletResponse response) throws URISyntaxException {
        // Exchange authorization code on id token
        WebClient webClient = WebClient.create();

        MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();
        bodyValues.add("grant_type", "authorization_code");
        bodyValues.add("client_id", googleOauthProperties.getClientId());
        bodyValues.add("client_secret", googleOauthProperties.getClientSecret());
        bodyValues.add("redirect_uri", googleOauthProperties.getRedirectUri());
        bodyValues.add("code", googleOauthRequest.getAuthorizationCode());

        GoogleAccessTokenResponse googleAccessTokenResponse = webClient.post()
                .uri(new URI(googleOauthProperties.getTokenUrl()))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(bodyValues))
                .retrieve()
                .bodyToMono(GoogleAccessTokenResponse.class)
                .block();

        if (googleAccessTokenResponse == null) {
            throw new TokenNotFoundException("Token not found");
        }
        String authenticatedUserEmail = getEmailFromGoogleIdToken(googleAccessTokenResponse.getIdToken());

        UserDetails user = userDetailsService.loadUserByUsername(authenticatedUserEmail);
        String[] tokens = jwtUtils.generateTokens(user);
        tokenService.saveNewToken(tokens[1], user.getUsername(), response);
        log.info("New token: {}", tokens[0]);
        return new JwtResponse(tokens[0]);
    }

    private String getRefreshFromCookie(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refreshToken")) {
                return cookie.getValue();
            }
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

    private String getEmailFromGoogleIdToken(String googleIdToken) {
        String jwtPayload = googleIdToken.split("\\.")[1];
        byte[] decodedPayloadBytes = Base64.getDecoder().decode(jwtPayload);
        String decodedPayload =  new String(decodedPayloadBytes);
        Map<String, String> convertedDecodedPayload = convertStringToMap(decodedPayload);

        return convertedDecodedPayload.get("email");
    }

    private Map<String, String> convertStringToMap(String stringMap) {
        stringMap = stringMap.substring(1, stringMap.length() - 1);
        String[] keyValuePairs = stringMap.split(",");
        Map<String, String> map = new HashMap<>();

        for (String pair : keyValuePairs) {
            String[] entry = pair.split(":");
            map.put(entry[0].substring(1, entry[0].length() - 1).trim(), entry[1].substring(1, entry[1].length() - 1).trim());          //add them to the hashmap and trim whitespaces
        }
        return map;
    }
}
