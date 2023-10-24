package project.vilsoncake.Flowt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import project.vilsoncake.Flowt.entity.enumerated.Sex;

import static project.vilsoncake.Flowt.constant.PatternConst.*;

@Data
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
}
