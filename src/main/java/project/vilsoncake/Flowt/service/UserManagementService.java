package project.vilsoncake.Flowt.service;

import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.dto.UrlAvatarRequest;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.exception.InvalidExtensionException;
import project.vilsoncake.Flowt.exception.MinioFileException;

import java.util.Map;

public interface UserManagementService {
    Map<String, String> addUserAvatarByUsername(String authHeader, MultipartFile avatar) throws MinioFileException, InvalidExtensionException;
    Map<String, String> addUserAvatarUrl(String authHeader, UrlAvatarRequest urlAvatarRequest);
    Map<String, String> addUserProfileHeaderByUsername(String authHeader, MultipartFile image) throws MinioFileException, InvalidExtensionException;
    byte[] getUserAvatarByUsername(String username) throws MinioFileException;
    byte[] getUserProfileHeaderByUsername(String username) throws MinioFileException;
    boolean removeUserAvatar(UserEntity user);
    Map<String, String> subscribeToUser(String authHeader, String username);
    Map<String, String> unsubscribeToUser(String authHeader, String username);
}
