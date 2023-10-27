package project.vilsoncake.Flowt.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "playlist_avatar")
@Data
@NoArgsConstructor
public class PlaylistAvatarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avatar_id", updatable = false)
    private Long avatarId;
    @Column(name = "filename")
    private String filename;
    @Column(name = "size")
    private String size = "0";
    @OneToOne
    @JoinColumn(name = "playlist_id")
    private PlaylistEntity playlist;

    public PlaylistAvatarEntity(PlaylistEntity playlist) {
        this.filename = generateUUID();
        this.playlist = playlist;
    }

    private String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
