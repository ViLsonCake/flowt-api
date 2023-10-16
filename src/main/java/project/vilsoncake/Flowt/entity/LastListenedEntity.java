package project.vilsoncake.Flowt.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "last_listened")
@Data
@NoArgsConstructor
public class LastListenedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
    @ManyToMany
    private List<SongEntity> songs;
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private UserEntity user;

    public LastListenedEntity(UserEntity user) {
        this.songs = new ArrayList<>();
        this.user = user;
    }
}
