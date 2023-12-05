package project.vilsoncake.Flowt.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "last_listened_playlist")
@Data
@NoArgsConstructor
public class LastListenedPlaylistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
    @ManyToMany
    private List<PlaylistEntity> playlists;
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private UserEntity user;

    public LastListenedPlaylistEntity(UserEntity user) {
        this.playlists = new ArrayList<>();
        this.user = user;
    }
}
