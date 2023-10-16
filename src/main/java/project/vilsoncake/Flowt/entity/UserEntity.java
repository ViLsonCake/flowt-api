package project.vilsoncake.Flowt.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.vilsoncake.Flowt.entity.enumerated.Role;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static project.vilsoncake.Flowt.constant.PatternConst.REGEX_REGION_PATTERN;
import static project.vilsoncake.Flowt.constant.PatternConst.REGEX_USERNAME_PATTERN;

@Entity
@Table(name = "user_")
@Data
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false)
    private Long userId;
    @NotBlank(message = "Username cannot be empty")
    @Size(min = 3, max = 32, message = "Username size must be between 3 and 32 characters")
    @Pattern(regexp = REGEX_USERNAME_PATTERN, message = "Username not valid")
    @Column(name = "username", unique = true)
    private String username;
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email is not valid")
    @Column(name = "email", unique = true)
    private String email;
    @NotBlank(message = "Password cannot be empty")
    @Column(name = "password")
    private String password;
    @NotNull
    @Size(max = 255, message = "Description size must be less than 255 characters")
    @Column(name = "description")
    private String description = "";
    @NotNull
    @Pattern(regexp = REGEX_REGION_PATTERN, message = "Region is not valid")
    @Column(name = "region")
    private String region = "Earth";
    @Column(name = "email_verify")
    private boolean emailVerify = false;
    @Column(name = "artist_verify")
    private boolean artistVerify = false;
    @Column(name = "active")
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
    private LikedEntity liked = new LikedEntity(this);
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private LastListenedEntity lastListened = new LastListenedEntity(this);
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<FollowerEntity> subscribes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "follower")
    private List<FollowerEntity> followers;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<PlaylistEntity> playlists;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<SongEntity> songs;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<NotificationEntity> notifications;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "whom")
    private List<ReportEntity> receivedReports;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sender")
    private List<ReportEntity> sentReports;
}
