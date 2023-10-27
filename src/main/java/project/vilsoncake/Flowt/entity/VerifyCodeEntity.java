package project.vilsoncake.Flowt.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "verify_code")
@Data
@NoArgsConstructor
public class VerifyCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
    @NotNull(message = "Code cannot be null")
    @Column(name = "code", unique = true)
    private String code;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public VerifyCodeEntity(UserEntity user) {
        this.code = generateUUID();
        this.user = user;
    }

    private String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
