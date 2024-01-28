package project.vilsoncake.Flowt.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.vilsoncake.Flowt.dto.ArtistVerifyPageDto;
import project.vilsoncake.Flowt.dto.ArtistVerifyRequestDto;
import project.vilsoncake.Flowt.dto.ArtistVerifyResponseDto;
import project.vilsoncake.Flowt.entity.ArtistVerifyRequestEntity;
import project.vilsoncake.Flowt.entity.PersonalDataEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.entity.UserLinkEntity;
import project.vilsoncake.Flowt.exception.ArtistAlreadyVerifiedException;
import project.vilsoncake.Flowt.exception.ArtistVerifyRequestAlreadyCheckedException;
import project.vilsoncake.Flowt.exception.ArtistVerifyRequestNotFoundException;
import project.vilsoncake.Flowt.repository.ArtistVerifyRequestRepository;
import project.vilsoncake.Flowt.repository.LinkRepository;
import project.vilsoncake.Flowt.repository.PersonalDataRepository;
import project.vilsoncake.Flowt.service.ArtistVerifyService;
import project.vilsoncake.Flowt.service.MailVerifyService;
import project.vilsoncake.Flowt.service.UserService;
import project.vilsoncake.Flowt.utils.AuthUtils;

import java.util.Map;

import static project.vilsoncake.Flowt.constant.MessageConst.ARTIST_VERIFY_MESSAGE;
import static project.vilsoncake.Flowt.constant.MessageConst.ARTIST_VERIFY_SUBJECT;

@Service
@RequiredArgsConstructor
public class ArtistVerifyServiceImpl implements ArtistVerifyService {

    private final ArtistVerifyRequestRepository artistVerifyRequestRepository;
    private final PersonalDataRepository personalDataRepository;
    private final LinkRepository linkRepository;
    private final UserService userService;
    private final MailVerifyService mailVerifyService;
    private final AuthUtils authUtils;

    @Transactional
    @Override
    public Map<String, String> saveNewArtistVerifyRequest(String authHeader, ArtistVerifyRequestDto artistVerifyRequestDto) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity sender = userService.getUserByUsername(username);

        if (sender.isArtistVerify() || artistVerifyRequestRepository.existsByUser(sender)) {
            throw new ArtistAlreadyVerifiedException(String.format("Artist \"%s\" is already verified", username));
        }

        ArtistVerifyRequestEntity artistVerifyRequest = new ArtistVerifyRequestEntity();
        artistVerifyRequest.setUser(sender);
        artistVerifyRequestRepository.save(artistVerifyRequest);

        PersonalDataEntity personalData = new PersonalDataEntity(
                artistVerifyRequestDto.getPersonalDataDto().getName(),
                artistVerifyRequestDto.getPersonalDataDto().getSurname(),
                artistVerifyRequestDto.getPersonalDataDto().getBirthDate(),
                artistVerifyRequestDto.getPersonalDataDto().getSex(),
                artistVerifyRequestDto.getPersonalDataDto().getCountry(),
                artistVerifyRequestDto.getPersonalDataDto().getPassportNumber(),
                artistVerifyRequest
        );
        personalDataRepository.save(personalData);
        artistVerifyRequestDto.getLinks().forEach(linkDto -> {
            UserLinkEntity userLinkEntity = new UserLinkEntity();
            userLinkEntity.setUrl(linkDto.getUrl());
            userLinkEntity.setRequest(artistVerifyRequest);
            linkRepository.save(userLinkEntity);
        });

        return Map.of("username", username);
    }

    @Transactional
    @Override
    public Map<String, String> verifyArtistByUsername(String username) {
        UserEntity user = userService.getUserByUsername(username);
        user.setArtistVerify(true);

        ArtistVerifyRequestEntity artistVerifyRequest = artistVerifyRequestRepository.findByUser(user).orElseThrow(() ->
                new ArtistVerifyRequestNotFoundException(String.format("Artist verify request for user \"%s\" not found", username)));

        if (artistVerifyRequest.isChecked()) {
            throw new ArtistVerifyRequestAlreadyCheckedException("Artist verify request already checked");
        }

        artistVerifyRequest.setChecked(true);

        Thread mailThread = new Thread(() -> {
            mailVerifyService.sendMessage(
                    user.getEmail(),
                    ARTIST_VERIFY_SUBJECT,
                    String.format(
                            ARTIST_VERIFY_MESSAGE,
                            user.getUsername()
                    )
            );
        });
        mailThread.start();

        return Map.of("message", String.format("Artist \"%s\" successfully verified", username));
    }

    @Override
    public Map<String, String> cancelVerifyRequestByUsername(String username) {
        UserEntity user = userService.getUserByUsername(username);
        ArtistVerifyRequestEntity artistVerifyRequest = artistVerifyRequestRepository.findByUser(user).orElseThrow(() ->
                new ArtistVerifyRequestNotFoundException(String.format("Artist verify request for user \"%s\" not found", username)));

        if (artistVerifyRequest.isChecked()) {
            throw new ArtistVerifyRequestAlreadyCheckedException("Artist verify request already checked");
        }

        artistVerifyRequest.setChecked(true);
        artistVerifyRequestRepository.save(artistVerifyRequest);

        return Map.of("message", "Artist request canceled");
    }

    @Override
    public ArtistVerifyPageDto getArtistVerifyRequestsByLatestDate(int page, int size) {
        Page<ArtistVerifyRequestEntity> artistVerifyRequests = artistVerifyRequestRepository.findAllByCheckedFalseOrderByCreatedAt(PageRequest.of(page, size));

        return new ArtistVerifyPageDto(
                artistVerifyRequests.getTotalPages(),
                artistVerifyRequests.getContent().stream().map(ArtistVerifyResponseDto::fromEntity).toList()
        );
    }
}
