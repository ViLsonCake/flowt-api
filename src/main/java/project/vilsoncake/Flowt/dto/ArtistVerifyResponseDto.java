package project.vilsoncake.Flowt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.vilsoncake.Flowt.entity.ArtistVerifyRequestEntity;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistVerifyResponseDto {
    private PersonalDataDto personalDataDto;
    private List<LinkDto> links;
    private String username;

    public static ArtistVerifyResponseDto fromEntity(ArtistVerifyRequestEntity artistVerifyRequestEntity) {
        ArtistVerifyResponseDto artistVerifyResponseDto = new ArtistVerifyResponseDto();
        artistVerifyResponseDto.setPersonalDataDto(PersonalDataDto.fromEntity(artistVerifyRequestEntity.getPersonalData()));
        artistVerifyResponseDto.setLinks(artistVerifyRequestEntity.getLinks().stream().map(linkEntity -> new LinkDto(linkEntity.getUrl())).toList());
        artistVerifyResponseDto.setUsername(artistVerifyRequestEntity.getUser().getUsername());
        return artistVerifyResponseDto;
    }
}
