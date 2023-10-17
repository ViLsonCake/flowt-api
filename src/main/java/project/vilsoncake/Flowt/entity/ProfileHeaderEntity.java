package project.vilsoncake.Flowt.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String size;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public ProfileHeaderEntity(String filename, String size, UserEntity user) {
        this.filename = filename;
        this.size = size;
        this.user = user;
    }
}
