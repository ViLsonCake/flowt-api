package project.vilsoncake.Flowt.utils;

import org.springframework.stereotype.Component;
import project.vilsoncake.Flowt.dto.ArtistVerifyRequestDto;
import project.vilsoncake.Flowt.entity.ArtistVerifyRequestEntity;
import project.vilsoncake.Flowt.entity.UserLinkEntity;
import project.vilsoncake.Flowt.entity.PersonalDataEntity;
import project.vilsoncake.Flowt.entity.UserEntity;

@Component
public class ArtistVerifyUtils {

    public ArtistVerifyRequestEntity artistVerifyDtoToEntity(ArtistVerifyRequestDto artistVerifyRequestDto, UserEntity user) {
        ArtistVerifyRequestEntity artistVerifyRequest = new ArtistVerifyRequestEntity();
        artistVerifyRequest.setPersonalData(
                new PersonalDataEntity(
                        artistVerifyRequestDto.getPersonalDataDto().getName(),
                        artistVerifyRequestDto.getPersonalDataDto().getSurname(),
                        artistVerifyRequestDto.getPersonalDataDto().getBirthDate(),
                        artistVerifyRequestDto.getPersonalDataDto().getSex(),
                        artistVerifyRequestDto.getPersonalDataDto().getCountry(),
                        artistVerifyRequestDto.getPersonalDataDto().getPassportNumber(),
                        artistVerifyRequest
                )
        );
        artistVerifyRequest.setLinks(artistVerifyRequestDto.getLinks().stream().map(linkDto -> new UserLinkEntity(linkDto.getUrl(), artistVerifyRequest)).toList());
        artistVerifyRequest.setUser(user);
        return artistVerifyRequest;
    }
}
