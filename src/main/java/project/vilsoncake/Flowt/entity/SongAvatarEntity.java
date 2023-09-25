package project.vilsoncake.Flowt.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String size;
    @OneToOne
    @JoinColumn(name = "song_id")
    private SongEntity song;

    public SongAvatarEntity(String filename, String size, SongEntity song) {
        this.filename = filename;
        this.size = size;
        this.song = song;
    }
}
