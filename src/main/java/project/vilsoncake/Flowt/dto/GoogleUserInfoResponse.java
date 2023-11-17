package project.vilsoncake.Flowt.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoogleUserInfoResponse {
    @JsonAlias("sub")
    private String sub;
    @JsonAlias("picture")
    private String picture;
    @JsonAlias("email")
    private String email;
    @JsonAlias("email_verified")
    private boolean emailVerified;
}
