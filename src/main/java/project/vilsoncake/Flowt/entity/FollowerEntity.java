package project.vilsoncake.Flowt.entity;

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
    private Long id;
    @Column(name = "follower_id")
    private Long followerId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
