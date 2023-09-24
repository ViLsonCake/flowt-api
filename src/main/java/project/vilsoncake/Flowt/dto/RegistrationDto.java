package project.vilsoncake.Flowt.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import project.vilsoncake.Flowt.constant.PatternConst;

@Data
public class RegistrationDto {
    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 3, max = 32, message = "Username size has been between 3 and 32 character")
    @Pattern(regexp = PatternConst.REGEX_USERNAME_PATTERN, message = "Username not valid")
    private String username;
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email is not valid")
    private String email;
    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, max = 16, message = "Username size has been between 8 and 16 character")
    private String password;
    @NotEmpty(message = "Confirm password cannot be empty")
    private String confirmPassword;
}
