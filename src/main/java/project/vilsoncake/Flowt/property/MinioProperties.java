package project.vilsoncake.Flowt.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {
    private String audioBucket;
    private String userAvatarBucket;
    private String userProfileHeaderBucket;
    private String songAvatarBucket;
    private String playlistAvatarBucket;
    private String url;
    private String accessKey;
    private String secretKey;
}
