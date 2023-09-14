package project.vilsoncake.Flowt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.vilsoncake.Flowt.entity.PlaylistEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistDto {
    private String name;
    private Boolean isPrivate;

    public static PlaylistDto fromPlaylist(PlaylistEntity playlist) {
        return new PlaylistDto(playlist.getName(), playlist.isPrivate());
    }
}
