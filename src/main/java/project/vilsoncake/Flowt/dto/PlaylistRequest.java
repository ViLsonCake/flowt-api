package project.vilsoncake.Flowt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.vilsoncake.Flowt.entity.PlaylistEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistRequest {
    private String name;
    private Boolean isPrivate;
    private String username;

    public static PlaylistRequest fromPlaylist(PlaylistEntity playlist) {
        return new PlaylistRequest(playlist.getName(), playlist.isPrivate(), playlist.getUser().getUsername());
    }
}
