package project.vilsoncake.Flowt.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "song")
@Data
@NoArgsConstructor
public class SongEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long songId;
    @Column(name = "name")
    private String name;
    @Column(name = "issue_year")
    private String issueYear;
    @Column(name = "genre")
    private String genre;
    @Column(name = "listens")
    private Long listens;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "song")
    private SongAvatarEntity songAvatar;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "song")
    private AudioFileEntity audioFile;
    @ManyToMany(mappedBy = "songs")
    private List<PlaylistEntity> playlists;
    @ManyToMany(mappedBy = "songs")
    private List<LikedEntity> liked;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
