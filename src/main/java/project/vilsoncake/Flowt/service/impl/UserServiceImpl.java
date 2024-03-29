package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.vilsoncake.Flowt.dto.*;
import project.vilsoncake.Flowt.entity.NotificationEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.entity.enumerated.NotificationType;
import project.vilsoncake.Flowt.exception.EmailAlreadyExistException;
import project.vilsoncake.Flowt.exception.InvalidPasswordCodeException;
import project.vilsoncake.Flowt.exception.PasswordsNotMatchException;
import project.vilsoncake.Flowt.exception.UsernameAlreadyExistException;
import project.vilsoncake.Flowt.repository.UserRepository;
import project.vilsoncake.Flowt.service.NotificationService;
import project.vilsoncake.Flowt.service.RedisService;
import project.vilsoncake.Flowt.service.UserService;
import project.vilsoncake.Flowt.service.UserVerifyService;
import project.vilsoncake.Flowt.utils.AuthUtils;
import project.vilsoncake.Flowt.utils.MailUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static project.vilsoncake.Flowt.entity.enumerated.Role.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserVerifyService userVerifyService;
    private final NotificationService notificationService;
    private final RedisService redisService;
    private final AuthUtils authUtils;
    private final MailUtils mailUtils;

    @Override
    public Map<String, String> addUser(RegistrationDto registrationDto) throws IOException {
        // Handle exception
        if (userRepository.existsUserByUsername(registrationDto.getUsername())) {
            throw new UsernameAlreadyExistException("Username already exists");
        }
        if (userRepository.existsUserByEmail(registrationDto.getEmail())) {
            throw new EmailAlreadyExistException("Email already exists");
        }
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            throw new PasswordsNotMatchException("Passwords don't match");
        }

        // Create new user and save
        UserEntity user = new UserEntity();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.getRoles().add(USER);
        user.getUserAvatar().setDefaultAvatarUrl(registrationDto.getUsername());
        userRepository.save(user);

        // Generate verify code and save
        userVerifyService.sendVerifyMailAndNotification(user);
        // Add verify notification
        notificationService.addNotification(
                NotificationType.MANDATORY,
                mailUtils.generateVerifyNotificationMessage(user.getEmail()),
                user
        );

        log.info("User '{}' saved", user.getUsername());
        return Map.of("message", String.format("User '%s' saved", user.getUsername()));
    }

    @Transactional
    @Override
    public AuthenticatedUserDto getAuthenticatedUserDto(String authHeader) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        return AuthenticatedUserDto.fromUser(getUserByUsername(username));
    }

    @Override
    public ExtendedUserDto getAuthenticatedUserDtoByUsername(String username) {
        return ExtendedUserDto.fromUser(getUserByUsername(username));
    }

    @Override
    public UserDto getPublicUserDtoByUsername(String username) {
        return UserDto.fromUser(getUserByUsername(username));
    }

    @Override
    public UsersPageDto getUsersDtoBySubstring(String substring, int page, int size) {
        Page<UserEntity> users = userRepository.findByUsernameContainingIgnoreCaseOrderByFollowers(substring, PageRequest.of(page, size));

        return new UsersPageDto(
                users.getTotalPages(),
                users.getContent()
                        .stream().filter(user -> !user.getRoles().contains(ADMIN) && !user.getRoles().contains(MODERATOR)).toList()
                        .stream().map(UserDto::fromUser).toList()
        );
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));
    }

    @Override
    public Map<String, String> changeUserPasswordByUsername(String authHeader, ChangePasswordDto changePasswordDto) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = getUserByUsername(username);

        // Validate password code
        if (!redisService.isValidUserCode(username, changePasswordDto.getCode())) {
            throw new InvalidPasswordCodeException("Invalid password code");
        }

        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepository.save(user);

        // Delete code from redis
        redisService.deleteByKeyFromCode(user.getUsername());

        return Map.of("username", username);
    }

    @Override
    public Map<String, String> restorePassword(RestorePasswordDto restorePasswordDto) {
        UserEntity user = getUserByEmail(restorePasswordDto.getEmail());

        // Validate password code
        if (!redisService.isValidUserCode(user.getUsername(), restorePasswordDto.getCode())) {
            throw new InvalidPasswordCodeException("Invalid password code");
        }

        user.setPassword(passwordEncoder.encode(restorePasswordDto.getNewPassword()));
        userRepository.save(user);

        // Delete code from redis
        redisService.deleteByKeyFromCode(user.getUsername());

        return Map.of("username", user.getUsername());
    }

    @Override
    public Map<String, List<NotificationEntity>> getUserNotifications(String authHeader) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = getUserByUsername(username);
        return Map.of("notifications", notificationService.getNotificationsByUser(user));
    }

    @Override
    public Map<String, List<PlaylistDto>> getUserPlaylists(String authHeader) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = getUserByUsername(username);

        return Map.of("playlists", user.getPlaylists().stream().map(PlaylistDto::fromPlaylistEntity).toList());
    }

    @Override
    public LastListenedSongsDto getLastListenedSongs(String authHeader) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = getUserByUsername(username);
        return new LastListenedSongsDto(user.getLastListened().getSongs().stream().map(SongDto::fromSongEntity).toList());
    }

    @Override
    public LastListenedPlaylistsDto getLastListenedPlaylists(String authHeader) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = getUserByUsername(username);
        return new LastListenedPlaylistsDto(user.getLastListenedPlaylists().getPlaylists().stream().map(PlaylistDto::fromPlaylistEntity).toList());
    }

    @Override
    public Map<String, String> deleteUser(String username) {
        UserEntity user = getUserByUsername(username);

        userRepository.delete(user);
        redisService.deleteByKeyFromWarning(user.getUsername());

        return Map.of("username", username);
    }
}
