package project.vilsoncake.Flowt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "liked")
@Data
@NoArgsConstructor
public class LikedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "liked_id", updatable = false)
    private Long likedId;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<SongEntity> songs;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public LikedEntity(UserEntity user) {
        this.songs = new ArrayList<>();
        this.user = user;
    }
}
