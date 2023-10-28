package project.vilsoncake.Flowt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.vilsoncake.Flowt.entity.PersonalDataEntity;
import project.vilsoncake.Flowt.entity.enumerated.Sex;

import static project.vilsoncake.Flowt.constant.PatternConst.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalDataDto {
    @NotBlank(message = "Name cannot be empty")
    @Pattern(regexp = REGEX_NAME_AND_SURNAME_PATTERN, message = "Name is not valid")
    private String name;
    @NotBlank(message = "Surname cannot be empty")
    @Pattern(regexp = REGEX_NAME_AND_SURNAME_PATTERN, message = "Surname is not valid")
    private String surname;
    @NotBlank(message = "Date of birth cannot be empty")
    @Pattern(regexp = REGEX_DATE_PATTERN, message = "Date of birth is not valid")
    private String birthDate;
    private Sex sex;
    @NotBlank(message = "Country cannot be empty")
    @Pattern(regexp = REGEX_REGION_PATTERN, message = "Country is not valid")
    private String country;
    @NotBlank(message = "Passport number cannot be empty")
    @Pattern(regexp = REGEX_PASSPORT_NUMBER_PATTERN, message = "Passport number is not valid")
    private String passportNumber;

    public static PersonalDataDto fromEntity(PersonalDataEntity personalDataEntity) {
        PersonalDataDto personalDataDto = new PersonalDataDto();
        personalDataDto.setName(personalDataEntity.getName());
        personalDataDto.setSurname(personalDataEntity.getSurname());
        personalDataDto.setBirthDate(personalDataEntity.getBirthDate());
        personalDataDto.setSex(personalDataEntity.getSex());
        personalDataDto.setCountry(personalDataEntity.getCountry());
        personalDataDto.setPassportNumber(personalDataEntity.getPassportNumber());
        return personalDataDto;
    }
}
