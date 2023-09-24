package project.vilsoncake.Flowt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "Message cannot be empty")
    @Size(min = 3, max = 255, message = "Message size must be between 3 and 255 characters")
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
