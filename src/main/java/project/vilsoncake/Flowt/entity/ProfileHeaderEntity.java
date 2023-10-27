package project.vilsoncake.Flowt.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "profile_header")
@Data
@NoArgsConstructor
public class ProfileHeaderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "filename")
    private String filename;
    @Column(name = "size")
    private String size = "0";
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public ProfileHeaderEntity(UserEntity user) {
        this.filename = generateUUID();
        this.user = user;
    }

    private String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
