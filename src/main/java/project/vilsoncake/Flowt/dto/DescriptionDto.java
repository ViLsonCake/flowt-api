package project.vilsoncake.Flowt.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DescriptionDto {
    @NotNull
    @Size(max = 255, message = "Description size must be less than 255 characters")
    private String newDescription;
}
