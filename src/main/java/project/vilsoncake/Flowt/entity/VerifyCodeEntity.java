package project.vilsoncake.Flowt.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "verify_code")
@Data
@NoArgsConstructor
public class VerifyCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Code cannot be null")
    @Column(name = "code", unique = true)
    private String code;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public VerifyCodeEntity(String code, UserEntity user) {
        this.code = code;
        this.user = user;
    }
}
