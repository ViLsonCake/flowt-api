package project.vilsoncake.Flowt.dto;

import lombok.Data;

@Data
public class RegistrationDto {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
}
