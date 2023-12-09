package project.vilsoncake.Flowt.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import project.vilsoncake.Flowt.entity.PlaylistEntity;

import java.util.List;

@Data
@NoArgsConstructor
public class PlaylistDto {
    private Long id;
    private String name;
    private List<SongDto> songs;
    private Long saveCount;
    private boolean isPrivate;
    private String username;

    public static PlaylistDto fromPlaylistEntity(PlaylistEntity playlistEntity) {
        PlaylistDto playlistDto = new PlaylistDto();
        playlistDto.setId(playlistEntity.getPlaylistId());
        playlistDto.setName(playlistEntity.getName());
        playlistDto.setPrivate(playlistEntity.isPrivate());
        playlistDto.setUsername(playlistEntity.getUser().getUsername());
        playlistDto.setSongs(playlistEntity.getSongs().stream().map(SongDto::fromSongEntity).toList());
        playlistDto.setSaveCount((long) playlistEntity.getSavedPlaylistEntities().size());

        return playlistDto;
    }
}
