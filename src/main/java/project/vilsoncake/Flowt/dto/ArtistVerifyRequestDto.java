package project.vilsoncake.Flowt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistVerifyRequestDto {
    private PersonalDataDto personalDataDto;
    private List<LinkDto> links;
}
