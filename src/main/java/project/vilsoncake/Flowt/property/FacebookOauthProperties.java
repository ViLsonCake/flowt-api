package project.vilsoncake.Flowt.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "facebook")
public class FacebookOauthProperties {
    private String userInfoUrl;
}
