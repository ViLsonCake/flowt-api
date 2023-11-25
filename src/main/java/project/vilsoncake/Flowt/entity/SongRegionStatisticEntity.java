package project.vilsoncake.Flowt.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "song_region_statistic")
@Data
@NoArgsConstructor
public class SongRegionStatisticEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "songRegionStatistic")
    private List<ListeningEntity> listeningEntities;
    @OneToOne
    @JoinColumn(name = "song_id")
    private SongEntity song;

    public SongRegionStatisticEntity(SongEntity song) {
        this.song = song;
    }
}
