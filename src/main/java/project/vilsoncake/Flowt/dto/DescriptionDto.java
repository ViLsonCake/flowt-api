package project.vilsoncake.Flowt.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DescriptionDto {
    @NotNull
    @Max(value = 255, message = "Description size must be less than 255 characters")
    private String newDescription;
}
