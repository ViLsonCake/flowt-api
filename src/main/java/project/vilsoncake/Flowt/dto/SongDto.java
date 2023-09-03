package project.vilsoncake.Flowt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongDto {
    private String name;
    private String issueYear;
    private String genre;
}
