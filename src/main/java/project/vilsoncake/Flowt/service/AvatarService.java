package project.vilsoncake.Flowt.service;

import org.springframework.web.multipart.MultipartFile;

public interface AvatarService {
    boolean saveAvatar(MultipartFile avatar, String uuid, Object user);
    boolean existsByFilename(String filename);
    boolean existsByEntity(Object entity);
    boolean deleteAvatar(Object entity);
    Object getByEntity(Object entity);
}
