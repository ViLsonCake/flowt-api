package project.vilsoncake.Flowt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static project.vilsoncake.Flowt.constant.PatternConst.REGEX_REGION_PATTERN;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionDto {
    @NotBlank(message = "New region cannot be empty")
    @Pattern(regexp = REGEX_REGION_PATTERN, message = "New region is not valid")
    private String newRegion;
}
