package project.vilsoncake.Flowt.entity;

import jakarta.persistence.*;
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
