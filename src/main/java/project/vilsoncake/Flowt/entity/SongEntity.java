package project.vilsoncake.Flowt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.vilsoncake.Flowt.entity.enumerated.Genre;

import java.util.List;

import static project.vilsoncake.Flowt.constant.PatternConst.*;

@Entity
@Table(name = "song")
@Data
@NoArgsConstructor
public class SongEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id", updatable = false)
    private Long songId;
    @NotBlank(message = "Song name cannot be empty")
    @Pattern(regexp = REGEX_SONG_NAME_PATTERN, message = "Song name is not valid")
    @Column(name = "name")
    private String name;
    @NotBlank(message = "Issue date cannot be empty")
    @Pattern(regexp = REGEX_ISSUE_DATE_PATTERN, message = "Issue date must be in format \"DD/MM/YY\"")
    @Column(name = "issue_year")
    private String issueYear;
    @Column(name = "genre")
    @Enumerated(EnumType.STRING)
    private Genre genre;
    @Min(value = 0, message = "Listens must be greater than zero")
    @Column(name = "listens")
    private Long listens;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "song")
    private SongAvatarEntity songAvatar = new SongAvatarEntity(this);
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "song")
    private SongRegionStatisticEntity regionStatistic = new SongRegionStatisticEntity(this);
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "song")
    private AudioFileEntity audioFile = new AudioFileEntity(this);
    @JsonIgnore
    @ManyToMany(mappedBy = "songs", cascade = CascadeType.ALL)
    private List<PlaylistEntity> playlists;
    @JsonIgnore
    @ManyToMany(mappedBy = "songs", cascade = CascadeType.ALL)
    private List<LikedEntity> liked;
    @JsonIgnore
    @ManyToMany(mappedBy = "songs", cascade = CascadeType.ALL)
    private List<LastListenedEntity> lastListened;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
