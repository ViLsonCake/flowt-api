package project.vilsoncake.Flowt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.vilsoncake.Flowt.entity.enumerated.NotificationType;

@Entity
@Table(name = "notification")
@Data
@NoArgsConstructor
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    private NotificationType type;
    @Column(name = "message")
    private String message;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public NotificationEntity(NotificationType type, String message, UserEntity user) {
        this.type = type;
        this.message = message;
        this.user = user;
    }
}
