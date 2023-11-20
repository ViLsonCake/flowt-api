package project.vilsoncake.Flowt.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FacebookUserInfoResponse {
    @JsonAlias("id")
    private String id;
    @JsonAlias("name")
    private String name;
    @JsonAlias("email")
    private String email;
}
