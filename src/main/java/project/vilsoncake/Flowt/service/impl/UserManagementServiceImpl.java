package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.dto.UrlAvatarRequest;
import project.vilsoncake.Flowt.entity.FollowerEntity;
import project.vilsoncake.Flowt.entity.ProfileHeaderEntity;
import project.vilsoncake.Flowt.entity.UserAvatarEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.entity.enumerated.NotificationType;
import project.vilsoncake.Flowt.exception.InvalidExtensionException;
import project.vilsoncake.Flowt.exception.MinioFileException;
import project.vilsoncake.Flowt.property.MinioProperties;
import project.vilsoncake.Flowt.service.*;
import project.vilsoncake.Flowt.utils.AuthUtils;
import project.vilsoncake.Flowt.utils.FileUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static project.vilsoncake.Flowt.constant.MessageConst.FOLLOW_MESSAGE;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserManagementServiceImpl implements UserManagementService {

    private final UserService userService;
    private final NotificationService notificationService;
    private final FollowerService followerService;
    private final AvatarService userAvatarService;
    private final MinioFileService minioFileService;
    private final MinioProperties minioProperties;
    private final FileUtils fileUtils;
    private final AuthUtils authUtils;

    @Transactional
    @Override
    public Map<String, String> addUserAvatarByUsername(String authHeader, MultipartFile avatar) throws InvalidExtensionException {
        // Validate file
        if (avatar.getOriginalFilename() != null && !fileUtils.isValidAvatarExtension(avatar.getOriginalFilename())) {
            throw new InvalidExtensionException("Invalid file extension (must be png or jpg)");
        }

        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);
        user.getUserAvatar().setSize(String.valueOf(avatar.getSize()));

        String filename = user.getUserAvatar().getFilename();
        user.getUserAvatar().setUserHaveAvatar(true);
        user.getUserAvatar().setDefaultAvatarUrl();

        // Save file data in minio storage
        minioFileService.saveFile(minioProperties.getUserAvatarBucket(), filename, avatar);

        return Map.of("username", username);
    }

    @Transactional
    @Override
    public Map<String, String> addUserAvatarUrl(String authHeader, UrlAvatarRequest urlAvatarRequest) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);
        user.getUserAvatar().setUserHaveAvatar(true);
        user.getUserAvatar().setAvatarUrl(urlAvatarRequest.getImageUrl());

        return Map.of("message", "Avatar url changed");
    }

    @Transactional
    @Override
    public Map<String, String> addUserProfileHeaderByUsername(String authHeader, MultipartFile image) throws MinioFileException, InvalidExtensionException {
        if (image.getOriginalFilename() != null && !fileUtils.isValidAvatarExtension(image.getOriginalFilename())) {
            throw new InvalidExtensionException("Invalid file extension (must be png or jpg)");
        }

        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);
        user.getProfileHeader().setSize(String.valueOf(image.getSize()));
        String filename = user.getProfileHeader().getFilename();

        // Save file data in minio storage
        minioFileService.saveFile(minioProperties.getUserProfileHeaderBucket(), filename, image);

        return Map.of("username", username);
    }

    @Override
    public byte[] getUserAvatarByUsername(String username) throws MinioFileException {
        UserEntity user = userService.getUserByUsername(username);
        UserAvatarEntity userAvatar = user.getUserAvatar();

        if (userAvatar == null) {
            throw new MinioFileException("File not found");
        }

        return minioFileService.getFileContent(minioProperties.getUserAvatarBucket(), userAvatar.getFilename());
    }

    @Override
    public byte[] getUserProfileHeaderByUsername(String username) throws MinioFileException {
        UserEntity user = userService.getUserByUsername(username);
        ProfileHeaderEntity profileHeader = user.getProfileHeader();

        if (profileHeader == null) {
            throw new MinioFileException("File not found");
        }

        return minioFileService.getFileContent(minioProperties.getUserProfileHeaderBucket(), profileHeader.getFilename());
    }

    @Override
    public boolean removeUserAvatar(UserEntity user) {
        return userAvatarService.deleteAvatar(user);
    }

    @Transactional
    @Override
    public Map<String, String> subscribeToUser(String authHeader, String username) {
        String usernameFromToken = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity authenticatedUser = userService.getUserByUsername(usernameFromToken);
        UserEntity followedUser = userService.getUserByUsername(username);

        Map<String, String> response = new HashMap<>();
        List<String> users = authenticatedUser.getSubscribes().stream().map(user -> user.getFollower().getUsername()).toList();

        // Add user to followed and add authenticated user to followers followed user or remove
        if (!users.contains(followedUser.getUsername())) {
            authenticatedUser.getFollowers().add(new FollowerEntity(followedUser, authenticatedUser));
            // Send notification to followed user
            notificationService.addNotification(NotificationType.INFO, String.format(FOLLOW_MESSAGE, authenticatedUser.getUsername()), followedUser);
            response.put("message", String.format("Subscribe to user '%s'", followedUser.getUsername()));
        } else {
            response.put("message", String.format("User already subscribed to '%s'", followedUser.getUsername()));
        }
        return response;
    }

    @Override
    public Map<String, String> unsubscribeToUser(String authHeader, String username) {
        String usernameFromToken = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity authenticatedUser = userService.getUserByUsername(usernameFromToken);
        UserEntity followedUser = userService.getUserByUsername(username);

        Map<String, String> response = new HashMap<>();
        List<String> users = authenticatedUser.getSubscribes().stream().map(user -> user.getFollower().getUsername()).toList();

        if (users.contains(followedUser.getUsername())) {
            followerService.unsubscribeUser(authenticatedUser.getUserId(), followedUser.getUserId());
            response.put("message", String.format("Unsubscribe to user '%s'", followedUser.getUsername()));
        } else {
            response.put("message", String.format("User not subscribed to '%s'", followedUser.getUsername()));
        }
        return response;
    }
}
