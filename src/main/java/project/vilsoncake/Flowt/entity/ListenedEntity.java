package project.vilsoncake.Flowt.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.vilsoncake.Flowt.entity.enumerated.Genre;

import java.util.List;

@Entity
@Table(name = "listened")
@Data
@NoArgsConstructor
public class ListenedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "song_name")
    private String songName;
    @Column(name = "genre")
    @Enumerated(EnumType.STRING)
    private Genre genre;
    @Column(name = "author")
    private String author;
    @ManyToMany
    private List<UserListenedStatisticEntity> userListenedStatistics;

    public ListenedEntity(String songName, Genre genre, String author) {
        this.songName = songName;
        this.genre = genre;
        this.author = author;
    }
}
