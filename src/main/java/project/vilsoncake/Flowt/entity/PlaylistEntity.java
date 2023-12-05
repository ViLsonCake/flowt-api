package project.vilsoncake.Flowt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static project.vilsoncake.Flowt.constant.PatternConst.REGEX_PLAYLIST_NAME_PATTERN;

@Entity
@Table(name = "playlist")
@Data
@NoArgsConstructor
public class PlaylistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlist_id", updatable = false)
    private Long playlistId;
    @NotBlank(message = "Playlist name cannot be empty")
    @Pattern(regexp = REGEX_PLAYLIST_NAME_PATTERN, message = "Playlist name is not valid")
    @Column(name = "name")
    private String name;
    @ManyToMany
    private List<SongEntity> songs;
    @JsonIgnore
    @ManyToMany(mappedBy = "playlists")
    private List<SavedPlaylistEntity> savedPlaylistEntities;
    @JsonIgnore
    @ManyToMany(mappedBy = "playlists")
    private List<LastListenedPlaylistEntity> lastListened;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "playlist")
    private PlaylistAvatarEntity playlistAvatar = new PlaylistAvatarEntity(this);
    @Column(name = "is_private")
    private boolean isPrivate = true;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public PlaylistEntity(String name, List<SongEntity> songs, UserEntity user) {
        this.name = name;
        this.songs = songs;
        this.user = user;
    }
}
