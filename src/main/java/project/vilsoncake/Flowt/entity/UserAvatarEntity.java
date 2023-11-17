package project.vilsoncake.Flowt.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.vilsoncake.Flowt.constant.UrlConst;

import java.util.UUID;

@Entity
@Table(name = "user_avatar")
@Data
@NoArgsConstructor
public class UserAvatarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avatar_id", updatable = false)
    private Long avatarId;
    @Column(name = "filename", unique = true)
    private String filename;
    @Column(name = "size")
    private String size = "0";
    @Column(name = "avatar_url")
    private String avatarUrl;
    @Column(name = "user_have_avatar")
    private boolean userHaveAvatar = false;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public UserAvatarEntity(UserEntity user) {
        this.filename = generateUUID();
        this.avatarUrl = UrlConst.USERS_AVATAR_URL + user.getUsername();
        this.user = user;
    }

    private String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public void setDefaultAvatarUrl() {
        this.avatarUrl = UrlConst.USERS_AVATAR_URL + user.getUsername();
    }
}
