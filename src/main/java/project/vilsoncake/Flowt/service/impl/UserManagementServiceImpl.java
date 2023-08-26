package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
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
import project.vilsoncake.Flowt.repository.UserRepository;
import project.vilsoncake.Flowt.service.AvatarService;
import project.vilsoncake.Flowt.service.MinioFileService;
import project.vilsoncake.Flowt.service.UserManagementService;
import project.vilsoncake.Flowt.utils.AuthUtils;
import project.vilsoncake.Flowt.utils.FileUtils;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserManagementServiceImpl implements UserManagementService {

    private final UserRepository userRepository;
    @Qualifier("userAvatarServiceImpl")
    private final AvatarService avatarService;
    private final MinioFileService minioFileService;
    private final MinioConfig minioConfig;
    private final FileUtils fileUtils;
    private final AuthUtils authUtils;

    @Transactional
    @Override
    public Map<String, String> addUserAvatarByUsername(String authHeader, MultipartFile avatar) throws InvalidExtensionException {
        // Validate file
        if (!fileUtils.isValidExtension(avatar.getOriginalFilename()))
            throw new InvalidExtensionException("Invalid file extension (must be png or jpg)");

        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));

        String filename;

        if (!avatarService.existsByUser(user)) {
            // Generate filename
            filename = fileUtils.generateRandomUUID();
            // Save file info in sql
            avatarService.saveAvatar(avatar, filename, user);
        } else {
            filename = avatarService.getByUser(user).getFilename();
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
    public Boolean changeUserActiveByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));

        user.setActive(!user.isActive());
        userRepository.save(user);
        return user.isActive();
    }

    @Override
    public Map<String, String> subscribeToUser(String authHeader, String username) {
        String usernameFromToken = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity authenticatedUser = userRepository.findByUsername(usernameFromToken).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));

        UserEntity followedUser = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));

        // Add user to followed and add authenticated user to followers followed user
        authenticatedUser.getFollowers().add(new FollowerEntity(followedUser, authenticatedUser));
        userRepository.save(authenticatedUser);

        return Map.of("username", username);
    }

    @Override
    public Map<String, String> deleteUserByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));

        userRepository.delete(user);
        return Map.of("username", user.getUsername());
    }
}
