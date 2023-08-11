package project.vilsoncake.Flowt.entity;

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
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "playlist")
    private PlaylistAvatarEntity playlistAvatar;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
