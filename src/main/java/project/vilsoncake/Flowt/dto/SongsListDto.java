package project.vilsoncake.Flowt.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SongsListDto {
    private List<AddSongDto> songs;
}
