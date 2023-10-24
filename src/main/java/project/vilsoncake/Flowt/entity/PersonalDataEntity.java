package project.vilsoncake.Flowt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.vilsoncake.Flowt.constant.PatternConst;
import project.vilsoncake.Flowt.entity.enumerated.Sex;

import static project.vilsoncake.Flowt.constant.PatternConst.*;

@Entity
@Table(name = "personal_data")
@Data
@NoArgsConstructor
public class PersonalDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
    @NotBlank(message = "Name cannot be empty")
    @Pattern(regexp = REGEX_NAME_AND_SURNAME_PATTERN, message = "Name is not valid")
    @Column(name = "name")
    private String name;
    @NotBlank(message = "Surname cannot be empty")
    @Pattern(regexp = REGEX_NAME_AND_SURNAME_PATTERN, message = "Surname is not valid")
    @Column(name = "surname")
    private String surname;
    @NotBlank(message = "Date of birth cannot be empty")
    @Pattern(regexp = REGEX_DATE_PATTERN, message = "Date of birth is not valid")
    @Column(name = "birth_date")
    private String birthDate;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "sex")
    private Sex sex;
    @NotBlank(message = "Country cannot be empty")
    @Pattern(regexp = REGEX_REGION_PATTERN, message = "Country is not valid")
    @Column(name = "country")
    private String country;
    @NotBlank(message = "Passport number cannot be empty")
    @Pattern(regexp = REGEX_PASSPORT_NUMBER_PATTERN, message = "Passport number is not valid")
    @Column(name = "passport_number")
    private String passportNumber;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "request_id")
    private ArtistVerifyRequestEntity request;

    public PersonalDataEntity(String name, String surname, String birthDate, Sex sex, String country, String passportNumber, ArtistVerifyRequestEntity request) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.sex = sex;
        this.country = country;
        this.passportNumber = passportNumber;
        this.request = request;
    }
}
