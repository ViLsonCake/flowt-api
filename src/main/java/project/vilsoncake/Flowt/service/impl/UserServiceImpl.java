package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.vilsoncake.Flowt.dto.ChangePasswordDto;
import project.vilsoncake.Flowt.dto.RegistrationDto;
import project.vilsoncake.Flowt.dto.RestorePasswordDto;
import project.vilsoncake.Flowt.dto.UserDto;
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
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));
        return UserDto.fromUser(user);
    }

    @Override
    public UserDto getUserDtoByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));
        return UserDto.fromUser(user);
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));
    }

    @Override
    public Map<String, String> changeUserPasswordByUsername(String authHeader, ChangePasswordDto changePasswordDto) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));

        // Validate password code
        if(!redisService.isValidUserCode(username, changePasswordDto.getCode()))
            throw new InvalidPasswordCodeException("Invalid password code");

        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepository.save(user);

        // Delete code from redis
        redisService.deleteByKey(user.getUsername());

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
        redisService.deleteByKey(user.getUsername());

        return Map.of("username", user.getUsername());
    }

    @Override
    public Map<String, String> deleteUser(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));

        userRepository.delete(user);

        return Map.of(
                "username", username
        );
    }
}
