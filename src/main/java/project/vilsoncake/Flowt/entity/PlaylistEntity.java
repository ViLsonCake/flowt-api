package project.vilsoncake.Flowt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "playlist")
@Data
@NoArgsConstructor
public class PlaylistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playlistId;
    @Column(name = "name")
    private String name;
    @ManyToMany
    private List<SongEntity> songs;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "playlist")
    private PlaylistAvatarEntity playlistAvatar;
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
