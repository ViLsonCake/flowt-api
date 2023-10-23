package project.vilsoncake.Flowt.utils;

import org.springframework.stereotype.Component;
import project.vilsoncake.Flowt.dto.ArtistVerifyDto;
import project.vilsoncake.Flowt.entity.ArtistVerifyRequestEntity;
import project.vilsoncake.Flowt.entity.LinkEntity;
import project.vilsoncake.Flowt.entity.PersonalDataEntity;
import project.vilsoncake.Flowt.entity.UserEntity;

@Component
public class ArtistVerifyUtils {

    public ArtistVerifyRequestEntity artistVerifyDtoToEntity(ArtistVerifyDto artistVerifyDto, UserEntity user) {
        ArtistVerifyRequestEntity artistVerifyRequest = new ArtistVerifyRequestEntity();
        artistVerifyRequest.setPersonalData(
                new PersonalDataEntity(
                        artistVerifyDto.getPersonalDataDto().getName(),
                        artistVerifyDto.getPersonalDataDto().getSurname(),
                        artistVerifyDto.getPersonalDataDto().getBirthDate(),
                        artistVerifyDto.getPersonalDataDto().getSex(),
                        artistVerifyDto.getPersonalDataDto().getCountry(),
                        artistVerifyDto.getPersonalDataDto().getPassportNumber(),
                        artistVerifyRequest
                )
        );
        artistVerifyRequest.setLinks(artistVerifyDto.getLinks().stream().map(linkDto -> new LinkEntity(linkDto.getUrl(), artistVerifyRequest)).toList());
        artistVerifyRequest.setUser(user);
        return artistVerifyRequest;
    }
}
