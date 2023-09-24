package project.vilsoncake.Flowt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static project.vilsoncake.Flowt.constant.PatternConst.REGEX_PLAYLIST_NAME_PATTERN;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistNameDto {
    @NotBlank(message = "Playlist name cannot be empty")
    @Pattern(regexp = REGEX_PLAYLIST_NAME_PATTERN, message = "Playlist name is not valid")
    private String playlistName;
    @NotBlank(message = "Playlist name cannot be empty")
    @Pattern(regexp = REGEX_PLAYLIST_NAME_PATTERN, message = "New playlist name is not valid")
    private String newPlaylistName;
}
