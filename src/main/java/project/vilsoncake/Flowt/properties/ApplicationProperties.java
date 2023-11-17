package project.vilsoncake.Flowt.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "app")
public class ApplicationProperties {
    private String clientUrl;
    private String url;
    private String usersAvatarUrl;
    private String verifyUrl;
}
