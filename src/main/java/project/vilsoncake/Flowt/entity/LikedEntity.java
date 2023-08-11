package project.vilsoncake.Flowt.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "liked")
@Data
@NoArgsConstructor
public class LikedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likedId;
    @ManyToMany
    private List<SongEntity> songs;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
