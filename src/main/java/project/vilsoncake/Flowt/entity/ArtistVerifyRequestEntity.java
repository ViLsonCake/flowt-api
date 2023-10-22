package project.vilsoncake.Flowt.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "artist_verify_request")
@Data
@NoArgsConstructor
public class ArtistVerifyRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "request")
    private PersonalDataEntity personalData;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "request")
    private List<LinkEntity> links;
    @CurrentTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
