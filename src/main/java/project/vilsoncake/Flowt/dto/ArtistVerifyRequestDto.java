package project.vilsoncake.Flowt.dto;

import lombok.Data;

import java.util.List;

@Data
public class ArtistVerifyRequestDto {
    private PersonalDataDto personalDataDto;
    private List<LinkDto> links;
}
