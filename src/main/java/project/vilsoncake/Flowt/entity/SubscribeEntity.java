package project.vilsoncake.Flowt.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subscribe")
@Data
@NoArgsConstructor
public class SubscribeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "subscribed_id")
    private Long subscribedId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
