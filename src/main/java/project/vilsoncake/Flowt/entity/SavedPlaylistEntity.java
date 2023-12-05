package project.vilsoncake.Flowt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "saved_playlist")
@Data
@NoArgsConstructor
public class SavedPlaylistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
    @ManyToMany
    private List<PlaylistEntity> playlists;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public SavedPlaylistEntity(UserEntity user) {
        this.playlists = new ArrayList<>();
        this.user = user;
    }
}
