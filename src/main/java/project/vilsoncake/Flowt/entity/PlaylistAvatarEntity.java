package project.vilsoncake.Flowt.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "playlist_avatar")
@Data
@NoArgsConstructor
public class PlaylistAvatarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long avatarId;
    @Column(name = "filename")
    private String filename;
    @Column(name = "size")
    private String size;
    @OneToOne
    @JoinColumn(name = "playlist_id")
    private PlaylistEntity playlist;

    public PlaylistAvatarEntity(String filename, String size, PlaylistEntity playlist) {
        this.filename = filename;
        this.size = size;
        this.playlist = playlist;
    }
}
