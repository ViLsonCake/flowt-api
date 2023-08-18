package project.vilsoncake.Flowt.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_avatar")
@Data
@NoArgsConstructor
public class UserAvatarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long avatarId;
    @Column(name = "filename")
    private String filename;
    @Column(name = "size")
    private String size;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public UserAvatarEntity(String filename, String size, UserEntity user) {
        this.filename = filename;
        this.size = size;
        this.user = user;
    }
}
