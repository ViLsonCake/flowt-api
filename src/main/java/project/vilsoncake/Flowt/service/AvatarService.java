package project.vilsoncake.Flowt.service;

import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.entity.UserAvatarEntity;
import project.vilsoncake.Flowt.entity.UserEntity;

public interface AvatarService {
    boolean saveAvatar(MultipartFile avatar, String uuid, UserEntity user);
    boolean existsByFilename(String filename);
    boolean existsByUser(UserEntity user);
    UserAvatarEntity getByUser(UserEntity user);
}
