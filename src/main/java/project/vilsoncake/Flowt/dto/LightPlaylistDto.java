package project.vilsoncake.Flowt.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import project.vilsoncake.Flowt.entity.PlaylistEntity;

@Data
@NoArgsConstructor
public class LightPlaylistDto {
    private Long id;
    private String name;
    private String username;
    private int songCount;

    public static LightPlaylistDto fromEntity(PlaylistEntity playlistEntity) {
        LightPlaylistDto lightPlaylistDto = new LightPlaylistDto();
        lightPlaylistDto.setId(playlistEntity.getPlaylistId());
        lightPlaylistDto.setName(playlistEntity.getName());
        lightPlaylistDto.setUsername(playlistEntity.getUser().getUsername());
        lightPlaylistDto.setSongCount(playlistEntity.getSongs().size());
        return lightPlaylistDto;
    }
}
