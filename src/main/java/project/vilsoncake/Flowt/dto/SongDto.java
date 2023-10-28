package project.vilsoncake.Flowt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.vilsoncake.Flowt.entity.SongEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongDto {
    private Long songId;
    private String name;
    private String issueYear;
    private String genre;
    private Long listens;
    private String author;

    public static SongDto fromSongEntity(SongEntity song) {
        SongDto songDto = new SongDto();
        songDto.setSongId(song.getSongId());
        songDto.setName(song.getName());
        songDto.setIssueYear(song.getIssueYear());
        songDto.setGenre(song.getGenre());
        songDto.setListens(song.getListens());
        songDto.setAuthor(song.getUser().getUsername());
        return songDto;
    }
}
