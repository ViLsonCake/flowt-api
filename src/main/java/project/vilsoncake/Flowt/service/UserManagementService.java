package project.vilsoncake.Flowt.service;

import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.exception.InvalidExtensionException;
import project.vilsoncake.Flowt.exception.MinioFileException;

import java.util.Map;

public interface UserManagementService {
    Boolean addUserAvatarByUsername(String authHeader, MultipartFile avatar) throws MinioFileException, InvalidExtensionException;
    byte[] getUserAvatarByUsername(String username) throws MinioFileException;
    Boolean changeUserActiveByUsername(String username);
    Map<String, String> subscribeToUser(String authHeader, String username);
    Long deleteUserByUsername(String username);
}
