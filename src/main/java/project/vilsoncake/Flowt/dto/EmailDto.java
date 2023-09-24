package project.vilsoncake.Flowt.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email is not valid")
    private String newEmail;
}
