package project.vilsoncake.Flowt.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.config.MinioConfig;
import project.vilsoncake.Flowt.entity.FollowerEntity;
import project.vilsoncake.Flowt.entity.UserAvatarEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.exception.InvalidExtensionException;
import project.vilsoncake.Flowt.exception.MinioFileException;
import project.vilsoncake.Flowt.repository.FollowerRepository;
import project.vilsoncake.Flowt.repository.UserRepository;
import project.vilsoncake.Flowt.service.AvatarService;
import project.vilsoncake.Flowt.service.FollowerService;
import project.vilsoncake.Flowt.service.MinioFileService;
import project.vilsoncake.Flowt.service.UserManagementService;
import project.vilsoncake.Flowt.utils.AuthUtils;
import project.vilsoncake.Flowt.utils.FileUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserManagementServiceImpl implements UserManagementService {

    private final UserRepository userRepository;
    private final FollowerService followerService;
    private final AvatarService avatarService;
    private final MinioFileService minioFileService;
    private final MinioConfig minioConfig;
    private final FileUtils fileUtils;
    private final AuthUtils authUtils;

    public UserManagementServiceImpl(UserRepository userRepository, FollowerService followerService, @Qualifier("userAvatarServiceImpl") AvatarService avatarService, MinioFileService minioFileService, MinioConfig minioConfig, FileUtils fileUtils, AuthUtils authUtils) {
        this.userRepository = userRepository;
        this.followerService = followerService;
        this.avatarService = avatarService;
        this.minioFileService = minioFileService;
        this.minioConfig = minioConfig;
        this.fileUtils = fileUtils;
        this.authUtils = authUtils;
    }

    @Transactional
    @Override
    public Map<String, String> addUserAvatarByUsername(String authHeader, MultipartFile avatar) throws InvalidExtensionException {
        // Validate file
        if (!fileUtils.isValidAvatarExtension(avatar.getOriginalFilename()))
            throw new InvalidExtensionException("Invalid file extension (must be png or jpg)");

        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));

        String filename;

        if (!avatarService.existsByEntity(user)) {
            // Generate filename
            filename = fileUtils.generateRandomUUID();
            // Save file info in sql
            avatarService.saveAvatar(avatar, filename, user);
        } else {
            filename = ((UserAvatarEntity) avatarService.getByEntity(user)).getFilename();
        }

        // Save file data in minio storage
        minioFileService.saveFile(minioConfig.getUserAvatarBucket(), filename, avatar);

        return Map.of("username", username);
    }

    @Override
    public byte[] getUserAvatarByUsername(String username) throws MinioFileException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));

        UserAvatarEntity userAvatar = user.getUserAvatar();

        if (userAvatar == null) throw new MinioFileException("File not found");

        String avatarFilename = userAvatar.getFilename();

        return minioFileService.getFileContent(minioConfig.getUserAvatarBucket(), avatarFilename);
    }

    @Override
    public Map<String, String> subscribeToUser(String authHeader, String username) {
        String usernameFromToken = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity authenticatedUser = userRepository.findByUsername(usernameFromToken).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));

        UserEntity followedUser = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));

        Map<String, String> response = new HashMap<>();
        List<String> users = authenticatedUser.getSubscribes().stream().map(user -> user.getFollower().getUsername()).toList();

        // Add user to followed and add authenticated user to followers followed user or remove
        if (!users.contains(followedUser.getUsername())) {
            authenticatedUser.getFollowers().add(new FollowerEntity(followedUser, authenticatedUser));
            response.put("message", String.format("Subscribe to user '%s'", followedUser.getUsername()));
        } else {
            response.put("message", String.format("User already subscribed to '%s'", followedUser.getUsername()));
        }
        userRepository.save(authenticatedUser);

        return response;
    }

    @Override
    public Map<String, String> unsubscribeToUser(String authHeader, String username) {
        String usernameFromToken = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity authenticatedUser = userRepository.findByUsername(usernameFromToken).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));

        UserEntity followedUser = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));

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
