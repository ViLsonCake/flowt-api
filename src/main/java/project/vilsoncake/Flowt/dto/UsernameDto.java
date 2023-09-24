package project.vilsoncake.Flowt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.vilsoncake.Flowt.constant.PatternConst;

import static project.vilsoncake.Flowt.constant.PatternConst.REGEX_USERNAME_PATTERN;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsernameDto {
    @NotBlank(message = "Username cannot be empty")
    @Pattern(regexp = REGEX_USERNAME_PATTERN, message = "Username is not valid")
    private String newUsername;
}
