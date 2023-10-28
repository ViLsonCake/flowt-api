package project.vilsoncake.Flowt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import static project.vilsoncake.Flowt.constant.PatternConst.REGEX_URL_PATTERN;

@Data
@AllArgsConstructor
public class LinkDto {
    @NotBlank(message = "Url cannot be empty")
    @Pattern(regexp = REGEX_URL_PATTERN, message = "Url is not valid")
    private String url;
}
