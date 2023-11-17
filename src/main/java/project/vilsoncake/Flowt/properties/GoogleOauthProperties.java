package project.vilsoncake.Flowt.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "google")
public class GoogleOauthProperties {
    private String tokenUrl;
    private String userInfoUrl;
    private String clientId;
    private String clientSecret;
    private String redirectUri;
}
