package project.vilsoncake.Flowt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import static project.vilsoncake.Flowt.constant.PatternConst.REGEX_URL_PATTERN;

@Entity
@Table(name = "link")
@Data
@NoArgsConstructor
public class UserLinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
    @NotBlank(message = "Url cannot be empty")
    @Pattern(regexp = REGEX_URL_PATTERN, message = "Url is not valid")
    @Column(name = "url")
    private String url;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "request_id")
    private ArtistVerifyRequestEntity request;

    public UserLinkEntity(String url, ArtistVerifyRequestEntity request) {
        this.url = url;
        this.request = request;
    }
}
