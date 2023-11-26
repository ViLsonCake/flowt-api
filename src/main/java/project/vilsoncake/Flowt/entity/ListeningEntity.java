package project.vilsoncake.Flowt.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.vilsoncake.Flowt.entity.enumerated.Country;
import project.vilsoncake.Flowt.entity.enumerated.Region;

@Entity
@Table(name = "listening")
@Data
@NoArgsConstructor
public class ListeningEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "region")
    @Enumerated(EnumType.STRING)
    private Region region;
    @Column(name = "country")
    @Enumerated(EnumType.STRING)
    private Country country;
    @Column(name = "username")
    private String username;
    @ManyToOne
    @JoinColumn(name = "song_statistic_id")
    private SongRegionStatisticEntity songRegionStatistic;

    public ListeningEntity(Region region, Country country, String username, SongRegionStatisticEntity songRegionStatistic) {
        this.region = region;
        this.country = country;
        this.username = username;
        this.songRegionStatistic = songRegionStatistic;
    }
}
