package project.vilsoncake.Flowt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "link")
@Data
@NoArgsConstructor
public class LinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "url")
    private String url;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "request_id")
    private ArtistVerifyRequestEntity request;

    public LinkEntity(String url, ArtistVerifyRequestEntity request) {
        this.url = url;
        this.request = request;
    }
}
