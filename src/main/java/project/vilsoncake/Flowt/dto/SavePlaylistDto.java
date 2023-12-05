package project.vilsoncake.Flowt.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SavePlaylistDto {
    private String username;
    private String playlistName;
}
