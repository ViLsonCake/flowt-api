package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.entity.UserAvatarEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.repository.UserAvatarRepository;
import project.vilsoncake.Flowt.service.AvatarService;

@Service("userAvatarService")
@Slf4j
@RequiredArgsConstructor
public class UserAvatarServiceImpl implements AvatarService {

    private final UserAvatarRepository userAvatarRepository;

    @Override
    public boolean deleteAvatar(Object entity) {
        UserAvatarEntity userAvatar = userAvatarRepository.findByUser((UserEntity) entity);
        userAvatarRepository.delete(userAvatar);
        return true;
    }
}
