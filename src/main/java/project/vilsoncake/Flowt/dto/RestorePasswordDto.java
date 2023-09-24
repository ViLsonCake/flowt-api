package project.vilsoncake.Flowt.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestorePasswordDto {
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email is not valid")
    private String email;
    @NotBlank(message = "New password cannot be empty")
    private String newPassword;
    @Size(min = 1000, max = 9999, message = "Code must be between 1000 and 9999")
    private Integer code;
}
