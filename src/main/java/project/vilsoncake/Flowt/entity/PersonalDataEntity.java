package project.vilsoncake.Flowt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "personal_data")
@Data
@NoArgsConstructor
public class PersonalDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "birth_date")
    private String birthDate;
    @Column(name = "sex")
    private String sex;
    @Column(name = "country")
    private String country;
    @Column(name = "passport_number")
    private String passportNumber;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "request_id")
    private ArtistVerifyRequestEntity request;

    public PersonalDataEntity(String name, String surname, String birthDate, String sex, String country, String passportNumber, ArtistVerifyRequestEntity request) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.sex = sex;
        this.country = country;
        this.passportNumber = passportNumber;
        this.request = request;
    }
}
