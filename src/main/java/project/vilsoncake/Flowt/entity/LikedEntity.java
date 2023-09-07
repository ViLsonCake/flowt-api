package project.vilsoncake.Flowt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public LikedEntity(List<SongEntity> songs, UserEntity user) {
        this.songs = songs;
        this.user = user;
    }
}
