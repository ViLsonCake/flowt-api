package project.vilsoncake.Flowt.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "user_listened_statistic")
@Data
@NoArgsConstructor
public class UserListenedStatisticEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userListenedStatistics")
    private List<ListenedEntity> listenedEntities;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public UserListenedStatisticEntity(UserEntity user) {
        this.user = user;
    }
}
