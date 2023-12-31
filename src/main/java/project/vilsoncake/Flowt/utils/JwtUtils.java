package project.vilsoncake.Flowt.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import project.vilsoncake.Flowt.dto.JwtTokensDto;
import project.vilsoncake.Flowt.property.JwtProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtProperties jwtProperties;

    public JwtTokensDto generateTokens(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> rolesList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", rolesList);

        Date issuedDate = new Date();
        Date accessExpiredDate = new Date(issuedDate.getTime() + jwtProperties.getAccessLifetime());
        Date refreshExpiredDate = new Date(issuedDate.getTime() + daysToMillis(jwtProperties.getRefreshLifetime()));

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(accessExpiredDate)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getAccessSecret())
                .compact();

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(refreshExpiredDate)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getRefreshSecret())
                .compact();

        return new JwtTokensDto(accessToken, refreshToken);
    }

    public String getUsernameFromAccess(String token) {
        return getAllClaimsFromAccess(token).getSubject();
    }

    public List<String> getRolesFromAccess(String token) {
        return getAllClaimsFromAccess(token).get("roles", List.class);
    }

    private Claims getAllClaimsFromAccess(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getAccessSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsernameFromRefresh(String token) {
        return getAllClaimsFromRefresh(token).getSubject();
    }

    public List<String> getRolesFromRefresh(String token) {
        return getAllClaimsFromRefresh(token).get("roles", List.class);
    }

    private Claims getAllClaimsFromRefresh(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getRefreshSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    public Integer daysToMillis(Integer days) {
        return days * 24 * 60 * 60 * 1000;
    }
}
