package project.vilsoncake.Flowt.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "token")
@Data
@NoArgsConstructor
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id", updatable = false)
    private Long tokenId;
    @NotNull(message = "Token cannot be null")
    @Column(name = "token")
    private String token = "";
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public TokenEntity(UserEntity user) {
        this.user = user;
    }
}
