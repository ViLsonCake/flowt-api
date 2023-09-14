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
import project.vilsoncake.Flowt.entity.FollowerEntity;
import project.vilsoncake.Flowt.entity.PlaylistEntity;
import project.vilsoncake.Flowt.entity.SongEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.exception.EmailAlreadyExistException;
import project.vilsoncake.Flowt.exception.InvalidPasswordCodeException;
import project.vilsoncake.Flowt.exception.PasswordsNotMatchException;
import project.vilsoncake.Flowt.exception.UsernameAlreadyExistException;
import project.vilsoncake.Flowt.repository.UserRepository;
import project.vilsoncake.Flowt.service.RedisService;
import project.vilsoncake.Flowt.service.UserService;
import project.vilsoncake.Flowt.service.UserVerifyService;
import project.vilsoncake.Flowt.utils.AuthUtils;

import java.util.List;
import java.util.Map;

import static project.vilsoncake.Flowt.entity.Role.USER;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserVerifyService userVerifyService;
    private final RedisService redisService;
    private final AuthUtils authUtils;

    @Override
    public Map<String, String> addUser(RegistrationDto registrationDto) {
        // Handle exception
        if (userRepository.existsUserByUsername(registrationDto.getUsername())) throw new UsernameAlreadyExistException("Username already exists");
        if (userRepository.existsUserByEmail(registrationDto.getEmail())) throw new EmailAlreadyExistException("Email already exists");
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) throw new PasswordsNotMatchException("Passwords don't match");

        // Create new user and save
        UserEntity user = new UserEntity();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.getRoles().add(USER);
        userRepository.save(user);

        // Generate verify code and save
        userVerifyService.saveAndSendNewCode(user);

        log.info("User '{}' saved", user.getUsername());

        return Map.of("message", String.format("User '%s' saved", user.getUsername()));
    }

    @Transactional
    @Override
    public UserDto getAuthenticatedUserDto(String authHeader) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        return UserDto.fromUser(getUserByUsername(username));
    }

    @Override
    public UserDto getUserDtoByUsername(String username) {
        return UserDto.fromUser(getUserByUsername(username));
    }

    @Override
    public UsersPageDto getUsersDtoBySubstring(SubstringDto substringDto, int page, int size) {
        Page<UserEntity> users = userRepository.findByUsernameContainingIgnoreCase(substringDto.getSubstring(), PageRequest.of(page, size));

        return new UsersPageDto(
                users.getTotalPages(),
                users.getContent().stream().map(UserDto::fromUser).toList()
        );
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));
    }

    @Override
    public Map<String, String> changeUserPasswordByUsername(String authHeader, ChangePasswordDto changePasswordDto) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = getUserByUsername(username);

        // Validate password code
        if(!redisService.isValidUserCode(username, changePasswordDto.getCode()))
            throw new InvalidPasswordCodeException("Invalid password code");

        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepository.save(user);

        // Delete code from redis
        redisService.deleteByKeyFromCode(user.getUsername());

        return Map.of("username", username);
    }

    @Override
    public Map<String, String> restorePassword(RestorePasswordDto restorePasswordDto) {
        UserEntity user = userRepository.findByEmail(restorePasswordDto.getEmail()).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));

        // Validate password code
        if(!redisService.isValidUserCode(user.getUsername(), restorePasswordDto.getCode()))
            throw new InvalidPasswordCodeException("Invalid password code");

        user.setPassword(passwordEncoder.encode(restorePasswordDto.getNewPassword()));
        userRepository.save(user);

        // Delete code from redis
        redisService.deleteByKeyFromCode(user.getUsername());

        return Map.of("username", user.getUsername());
    }

    @Override
    public Map<String, List<String>> getUserSubscribesUsernames(String authHeader) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));

        List<FollowerEntity> subscribes = user.getSubscribes();
        List<String> usernames = subscribes.stream().map(subscribe -> subscribe.getFollower().getUsername()).toList();

        return Map.of("subscribes", usernames);
    }

    @Override
    public Map<String, List<String>> getUserFollowersUsernames(String authHeader) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = getUserByUsername(username);
        List<String> usernames = user.getFollowers().stream().map(follower -> follower.getUser().getUsername()).toList();

        return Map.of("followers", usernames);
    }

    @Override
    public Map<String, List<String>> getUserSongs(String authHeader) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = getUserByUsername(username);
        List<String> songNames = user.getSongs().stream().map(SongEntity::getName).toList();

        return Map.of("songs", songNames);
    }

    @Override
    public Map<String, List<PlaylistEntity>> getUserPlaylists(String authHeader) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = getUserByUsername(username);

        return Map.of("playlists", user.getPlaylists());
    }

    @Override
    public Map<String, String> deleteUser(String username) {
        UserEntity user = getUserByUsername(username);

        userRepository.delete(user);
        redisService.deleteByKeyFromWarning(user.getUsername());

        return Map.of("username", username);
    }
}
