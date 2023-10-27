package project.vilsoncake.Flowt.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "song_avatar")
@Data
@NoArgsConstructor
public class SongAvatarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avatar_id", updatable = false)
    private Long avatarId;
    @Column(name = "filename")
    private String filename;
    @Column(name = "size")
    private String size = "0";
    @OneToOne
    @JoinColumn(name = "song_id")
    private SongEntity song;

    public SongAvatarEntity(SongEntity song) {
        this.filename = generateUUID();
        this.song = song;
    }

    private String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
