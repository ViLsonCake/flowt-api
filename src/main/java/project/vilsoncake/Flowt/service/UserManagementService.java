package project.vilsoncake.Flowt.service;

import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.exception.InvalidExtensionException;
import project.vilsoncake.Flowt.exception.MinioFileException;

public interface UserManagementService {
    Boolean addUserAvatarByUsername(String authHeader, MultipartFile avatar) throws MinioFileException, InvalidExtensionException;
    byte[] getUserAvatarByUsername(String username) throws MinioFileException;
    Boolean changeUserActiveByUsername(String username);
    Long deleteUserByUsername(String username);
}
