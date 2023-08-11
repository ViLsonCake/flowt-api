package project.vilsoncake.Flowt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {
    private String audioBucket;
    private String userAvatarBucket;
    private String songAvatarBucket;
    private String playlistAvatarBucket;
    private String url;
    private String accessKey;
    private String secretKey;
}
