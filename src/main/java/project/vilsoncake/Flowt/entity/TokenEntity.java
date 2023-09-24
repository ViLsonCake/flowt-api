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
    private Long tokenId;
    @NotNull(message = "Token cannot be null")
    @Column(name = "token")
    private String token;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public TokenEntity(String token, UserEntity user) {
        this.token = token;
        this.user = user;
    }
}
