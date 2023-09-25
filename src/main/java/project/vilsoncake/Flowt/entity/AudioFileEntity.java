package project.vilsoncake.Flowt.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String size;
    @OneToOne
    @JoinColumn(name = "song_id")
    private SongEntity song;

    public AudioFileEntity(String filename, String size, SongEntity song) {
        this.filename = filename;
        this.size = size;
        this.song = song;
    }
}
