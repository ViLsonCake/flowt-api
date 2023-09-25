package project.vilsoncake.Flowt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "follower")
@Data
@NoArgsConstructor
public class FollowerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "follower_id")
    private UserEntity follower;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public FollowerEntity(UserEntity follower, UserEntity user) {
        this.follower = follower;
        this.user = user;
    }
}
