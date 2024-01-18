package project.vilsoncake.Flowt.dto;

import lombok.Data;
import project.vilsoncake.Flowt.entity.enumerated.Genre;

@Data
public class GenreCountDto {
    private Genre genre;
    private int listens;
}
