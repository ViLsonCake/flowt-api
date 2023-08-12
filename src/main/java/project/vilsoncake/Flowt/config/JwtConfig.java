package project.vilsoncake.Flowt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    private String accessSecret;
    private String refreshSecret;
    private Integer accessLifetime;
    private Integer refreshLifetime;
}
