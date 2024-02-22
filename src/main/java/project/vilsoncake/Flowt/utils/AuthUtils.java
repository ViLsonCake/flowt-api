package project.vilsoncake.Flowt.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtils {

    private final JwtUtils jwtUtils;

    public String getUsernameFromAuthHeader(String authHeader) {
        String jwt;
        String username = "";

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            try {
                username = jwtUtils.getUsernameFromAccess(jwt);
            } catch (ExpiredJwtException e) {
                username = e.getClaims().getSubject();
            } catch (SignatureException | MalformedJwtException ignored) {

            }
        }
        return username;
    }
}
