package project.vilsoncake.Flowt.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GoogleAccessTokenResponse {
    @JsonAlias("access_token")
    private String accessToken;
    @JsonAlias("expires_in")
    private int expiresIn;
    @JsonAlias("scope")
    private String scope;
    @JsonAlias("token_type")
    private String tokenType;
    @JsonAlias("id_token")
    private String idToken;
}
