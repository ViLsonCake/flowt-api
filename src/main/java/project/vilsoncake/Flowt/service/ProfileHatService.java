package project.vilsoncake.Flowt.service;

import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.entity.ProfileHeaderEntity;
import project.vilsoncake.Flowt.entity.UserEntity;

public interface ProfileHatService {
    boolean existsByUser(UserEntity user);
    ProfileHeaderEntity getByUser(UserEntity user);
    boolean saveImage(MultipartFile image, String uuid, UserEntity user);
}
