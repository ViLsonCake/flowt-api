package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.vilsoncake.Flowt.dto.*;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.exception.EmailAlreadyExistException;
import project.vilsoncake.Flowt.exception.UsernameAlreadyExistException;
import project.vilsoncake.Flowt.repository.UserRepository;
import project.vilsoncake.Flowt.service.ChangeUserService;
import project.vilsoncake.Flowt.utils.AuthUtils;

import java.util.Map;

import static project.vilsoncake.Flowt.entity.Role.MODERATOR;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChangeUserServiceImpl implements ChangeUserService {

    private final UserRepository userRepository;
    private final AuthUtils authUtils;

    @Override
    public Map<String, String> changeUserUsername(String authHeader, UsernameDto usernameDto) {
        // Validate new username
        if (userRepository.existsUserByUsername(usernameDto.getNewUsername()))
            throw new UsernameAlreadyExistException("Username already exits");

        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));

        user.setUsername(usernameDto.getNewUsername());
        userRepository.save(user);

        return Map.of("username", usernameDto.getNewUsername());
    }

    @Override
    public Map<String, String> changeUserEmail(String authHeader, EmailDto emailDto) {
        // Validate new email
        if (userRepository.existsUserByEmail(emailDto.getNewEmail()))
            throw new EmailAlreadyExistException("Email already exits");

        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));

        user.setEmail(emailDto.getNewEmail());
        userRepository.save(user);

        return Map.of("email", emailDto.getNewEmail());
    }

    @Override
    public Map<String, String> changeUserRegion(String authHeader, RegionDto regionDto) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));

        user.setRegion(regionDto.getNewRegion());
        userRepository.save(user);

        return Map.of("region", regionDto.getNewRegion());
    }

    @Override
    public Map<String, String> changeUserDescription(String authHeader, DescriptionDto descriptionDto) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));

        user.setDescription(descriptionDto.getNewDescription());
        userRepository.save(user);

        return Map.of("description", descriptionDto.getNewDescription());
    }

    @Override
    public ChangeAuthorityDto changeUserAuthority(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));

        // Add to user moderator role or delete if user has
        if (!user.getRoles().contains(MODERATOR)) user.getRoles().add(MODERATOR);
        else user.getRoles().remove(MODERATOR);
        userRepository.save(user);

        return new ChangeAuthorityDto(user.getUsername(), user.getEmail(), user.getRoles(), user.isActive());
    }

    @Override
    public Map<String, Boolean> changeUserActive(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));

        user.setActive(!user.isActive());
        userRepository.save(user);

        return Map.of("active", user.isActive());
    }
}
