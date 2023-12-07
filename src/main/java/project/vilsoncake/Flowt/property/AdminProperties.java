package project.vilsoncake.Flowt.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "admin")
public class AdminProperties {
    private String username;
    private String email;
    private String password;
}
