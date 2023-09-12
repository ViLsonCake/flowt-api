package project.vilsoncake.Flowt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.vilsoncake.Flowt.entity.SongEntity;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSongsDto {
    private int count;
    private List<SongEntity> songs;
}
