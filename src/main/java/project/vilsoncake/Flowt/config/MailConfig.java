package project.vilsoncake.Flowt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "jwt")
public class MailConfig {
    private String host;
    private String port;
    private String username;
    private String password;
}
