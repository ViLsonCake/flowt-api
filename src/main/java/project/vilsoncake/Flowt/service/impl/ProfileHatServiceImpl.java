package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.entity.ProfileHatEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.repository.ProfileHatRepository;
import project.vilsoncake.Flowt.service.ProfileHatService;

@Service
@RequiredArgsConstructor
public class ProfileHatServiceImpl implements ProfileHatService {

    private final ProfileHatRepository profileHatRepository;

    @Override
    public boolean existsByUser(UserEntity user) {
        return profileHatRepository.existsByUser(user);
    }

    @Override
    public ProfileHatEntity getByUser(UserEntity user) {
        return profileHatRepository.getByUser(user);
    }

    @Override
    public boolean saveImage(MultipartFile image, String uuid, UserEntity user) {
        ProfileHatEntity profileHat = new ProfileHatEntity(uuid, String.valueOf(image.getSize()), user);
        profileHatRepository.save(profileHat);
        return true;
    }
}
