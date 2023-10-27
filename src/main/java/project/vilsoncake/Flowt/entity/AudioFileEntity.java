package project.vilsoncake.Flowt.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "audio_file")
@Data
@NoArgsConstructor
public class AudioFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id", updatable = false)
    private Long fileId;
    @Column(name = "filename")
    private String filename;
    @Column(name = "size")
    private String size = "0";
    @OneToOne
    @JoinColumn(name = "song_id")
    private SongEntity song;

    public AudioFileEntity(SongEntity song) {
        this.filename = generateUUID();
        this.song = song;
    }

    private String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
