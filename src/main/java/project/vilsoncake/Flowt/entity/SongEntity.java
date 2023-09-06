package project.vilsoncake.Flowt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "song")
    private SongAvatarEntity songAvatar;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "song")
    private AudioFileEntity audioFile;
    @JsonIgnore
    @ManyToMany(mappedBy = "songs")
    private List<PlaylistEntity> playlists;
    @JsonIgnore
    @ManyToMany(mappedBy = "songs")
    private List<LikedEntity> liked;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
