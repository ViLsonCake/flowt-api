package project.vilsoncake.Flowt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private String clientUrl;
    private String url;
    private String verifyUrl;
}
