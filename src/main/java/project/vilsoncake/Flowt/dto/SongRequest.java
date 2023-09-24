package project.vilsoncake.Flowt.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static project.vilsoncake.Flowt.constant.PatternConst.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongRequest {
    @NotEmpty(message = "Song name cannot be empty")
    @Pattern(regexp = REGEX_SONG_NAME_PATTERN, message = "Song name is not valid")
    private String name;
    @NotEmpty(message = "Issue date cannot be empty")
    @Pattern(regexp = REGEX_ISSUE_DATE_PATTERN, message = "Issue date must be in format \"DD/MM/YY\"")
    private String issueYear;
    @NotEmpty(message = "Genre cannot be empty")
    @Pattern(regexp = REGEX_GENRE_PATTERN, message = "Genre is not valid")
    private String genre;
}
