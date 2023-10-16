package project.vilsoncake.Flowt.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "spring.mail")
public class MailProperties {
    private String host;
    private String port;
    private String username;
    private String password;
}
