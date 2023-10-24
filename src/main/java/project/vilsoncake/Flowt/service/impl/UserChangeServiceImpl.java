package project.vilsoncake.Flowt.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.vilsoncake.Flowt.dto.*;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.entity.enumerated.NotificationType;
import project.vilsoncake.Flowt.exception.EmailAlreadyExistException;
import project.vilsoncake.Flowt.exception.UsernameAlreadyExistException;
import project.vilsoncake.Flowt.repository.UserRepository;
import project.vilsoncake.Flowt.service.*;
import project.vilsoncake.Flowt.utils.AuthUtils;
import project.vilsoncake.Flowt.utils.JwtUtils;

import java.util.Map;

import static project.vilsoncake.Flowt.entity.enumerated.ReportContentType.DESCRIPTION;
import static project.vilsoncake.Flowt.entity.enumerated.ReportContentType.NAME;
import static project.vilsoncake.Flowt.entity.enumerated.Role.MODERATOR;
import static project.vilsoncake.Flowt.entity.enumerated.WhomReportType.USER;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserChangeServiceImpl implements UserChangeService {

    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final RedisService redisService;
    private final TokenService tokenService;
    private final ReportService reportService;
    private final NotificationService notificationService;
    private final JwtUtils jwtUtils;
    private final AuthUtils authUtils;

    @Override
    public Map<String, String> changeUserUsername(String authHeader, UsernameDto usernameDto, HttpServletResponse response) {
        // Validate new username
        if (userRepository.existsUserByUsername(usernameDto.getNewUsername())) {
            throw new UsernameAlreadyExistException("Username already exits");
        }

        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));

        // Check whether the user is obliged to change username due to violations
        if (redisService.getValueFromWarning(username) != null) {
            redisService.deleteByKeyFromWarning(username);
            notificationService.removeNotificationByType(NotificationType.WARNING);
        }

        user.setUsername(usernameDto.getNewUsername());
        userRepository.save(user);

        reportService.cancelReportByWhomTypeAndContentTypeAndWhom(USER, NAME, user);

        // Create and save a new pair of tokens
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String[] tokens = jwtUtils.generateTokens(userDetails);
        tokenService.saveNewToken(tokens[1], user.getUsername(), response);

        return Map.of("token", tokens[0]);
    }

    @Override
    public Map<String, String> changeUserEmail(String authHeader, EmailDto emailDto) {
        // Validate new email
        if (userRepository.existsUserByEmail(emailDto.getNewEmail())) {
            throw new EmailAlreadyExistException("Email already exits");
        }

        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));

        // Save email
        user.setEmail(emailDto.getNewEmail());
        userRepository.save(user);

        return Map.of("email", emailDto.getNewEmail());
    }

    @Override
    public Map<String, String> changeUserRegion(String authHeader, RegionDto regionDto) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));

        // Save new region
        user.setRegion(regionDto.getNewRegion());
        userRepository.save(user);

        return Map.of("region", regionDto.getNewRegion());
    }

    @Override
    public Map<String, String> changeUserDescription(String authHeader, DescriptionDto descriptionDto) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));

        // Save new description
        user.setDescription(descriptionDto.getNewDescription());
        userRepository.save(user);

        reportService.cancelReportByWhomTypeAndContentTypeAndWhom(USER, DESCRIPTION, user);

        return Map.of("description", descriptionDto.getNewDescription());
    }

    @Override
    public ChangeAuthorityDto changeUserAuthority(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));

        // Add to user moderator role or delete if user has
        if (!user.getRoles().contains(MODERATOR)) {
            user.getRoles().add(MODERATOR);
        } else {
            user.getRoles().remove(MODERATOR);
        }
        userRepository.save(user);

        return new ChangeAuthorityDto(user.getUsername(), user.getEmail(), user.getRoles(), user.isActive());
    }

    @Override
    public Map<String, Boolean> changeUserActive(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));

        // Save active
        user.setActive(!user.isActive());
        userRepository.save(user);

        return Map.of("active", user.isActive());
    }
}
