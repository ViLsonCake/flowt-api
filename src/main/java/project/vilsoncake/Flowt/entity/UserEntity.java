package project.vilsoncake.Flowt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user_")
@Data
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "description")
    private String description = "";
    @Column(name = "region")
    private String region = "Earth";
    private boolean emailVerify = false;
    private boolean artistVerify = false;
    private boolean active = true;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<TokenEntity> token;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private VerifyCodeEntity verifyCode;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private UserAvatarEntity userAvatar;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private LikedEntity liked;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "follower")
    private List<FollowerEntity> subscribes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<FollowerEntity> followers;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<PlaylistEntity> playlists;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<SongEntity> songs;
}
