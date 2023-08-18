package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.entity.UserAvatarEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.repository.UserAvatarRepository;
import project.vilsoncake.Flowt.service.AvatarService;

@Service("userAvatarServiceImpl")
@Slf4j
@RequiredArgsConstructor
public class UserAvatarServiceImpl implements AvatarService {

    private final UserAvatarRepository userAvatarRepository;

    @Override
    public boolean saveAvatar(MultipartFile avatar, String uuid, UserEntity user) {
        UserAvatarEntity userAvatar = new UserAvatarEntity(
                uuid, String.valueOf(avatar.getSize()), user
        );
        userAvatarRepository.save(userAvatar);
        return true;
    }

    @Override
    public UserAvatarEntity getByUser(UserEntity user) {
        return userAvatarRepository.findByUser(user);
    }

    @Override
    public boolean existsByFilename(String filename) {
        return userAvatarRepository.existsByFilename(filename);
    }

    @Override
    public boolean existsByUser(UserEntity user) {
        return userAvatarRepository.existsByUser(user);
    }
}
