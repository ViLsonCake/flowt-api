package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.vilsoncake.Flowt.property.ApplicationProperties;
import project.vilsoncake.Flowt.entity.*;
import project.vilsoncake.Flowt.entity.enumerated.NotificationType;
import project.vilsoncake.Flowt.exception.AccountAlreadyVerifiedException;
import project.vilsoncake.Flowt.exception.VerifyCodeNotFoundException;
import project.vilsoncake.Flowt.repository.UserRepository;
import project.vilsoncake.Flowt.repository.VerifyCodeRepository;
import project.vilsoncake.Flowt.service.*;
import project.vilsoncake.Flowt.utils.AuthUtils;
import project.vilsoncake.Flowt.utils.MailUtils;
import project.vilsoncake.Flowt.utils.ReportUtils;

import java.util.Map;
import java.util.UUID;

import static project.vilsoncake.Flowt.constant.MessageConst.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserVerifyServiceImpl implements UserVerifyService {

    private final VerifyCodeRepository verifyCodeRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final MailVerifyService mailVerifyService;
    private final RedisService redisService;
    private final ApplicationProperties applicationProperties;
    private final AuthUtils authUtils;
    private final MailUtils mailUtils;
    private final ReportUtils reportUtils;

    @Override
    public Map<String, String> sendVerifyMailAndNotification(UserEntity user) {
        // Send verify mail
        Thread mailThread = new Thread(() -> {
            mailVerifyService.sendMessage(
                    user.getEmail(),
                    VERIFY_EMAIL_SUBJECT,
                    String.format(
                            VERIFY_EMAIL_TEXT,
                            user.getUsername(),
                            (applicationProperties.getVerifyUrl() + user.getVerifyCode().getCode())
                    )
            );
        });
        mailThread.start();

        return Map.of("username", user.getUsername());
    }

    @Transactional
    @Override
    public Map<String, String> verifyUserEmail(String code) throws VerifyCodeNotFoundException, AccountAlreadyVerifiedException {
        VerifyCodeEntity verifyCodeEntity = verifyCodeRepository.findByCode(code).orElseThrow(() ->
                new VerifyCodeNotFoundException("Verify code don't exist"));

        UserEntity user = verifyCodeEntity.getUser();

        if (user.isEmailVerify()) {
            notificationService.addNotification(
                    NotificationType.INFO,
                    mailUtils.generateAlreadyVerifiedEmailMessage(user.getEmail()),
                    user
            );
            return Map.of("message", String.format("Email %s is already verified", user.getEmail()));
        }

        user.setEmailVerify(true);
        // Add notification about verifying email and remove notification with the requirement to confirm email
        notificationService.addNotification(
                NotificationType.INFO,
                mailUtils.generateSuccessVerifyEmailMessage(user.getEmail()),
                user
        );
        notificationService.removeNotificationByType(NotificationType.MANDATORY);

        return Map.of("message", String.format("Email %s is successfully verified", user.getEmail()));
    }

    @Override
    public Map<String, String> sendChangePasswordMessageByUsername(String authHeader) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userRepository.findByUsernameIgnoreCase(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));

        // Generate code and save in redis
        String code = redisService.saveNewPasswordCode(username);

        Thread mailThread = new Thread(() -> {
            mailVerifyService.sendMessage(
                    user.getEmail(),
                    RESTORE_PASSWORD_SUBJECT,
                    String.format(
                            RESTORE_PASSWORD_TEXT,
                            user.getUsername(),
                            code
                    )
            );
        });
        mailThread.start();

        return Map.of("username", user.getUsername());
    }

    @Override
    public Map<String, String> sendChangePasswordMessageByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));

        // Generate code and save in redis
        String code = redisService.saveNewPasswordCode(user.getUsername());

        Thread mailThread = new Thread(() -> {
            mailVerifyService.sendMessage(
                    user.getEmail(),
                    RESTORE_PASSWORD_SUBJECT,
                    String.format(
                            RESTORE_PASSWORD_TEXT,
                            user.getUsername(),
                            code
                    )
            );
        });
        mailThread.start();

        return Map.of("email", email);
    }

    @Override
    public boolean sendWarningMessage(ReportEntity report) {
        String message = reportUtils.createReportMessage(report);
        Thread mailThread = new Thread(() -> {
            mailVerifyService.sendMessage(
                    report.getWhom().getEmail(),
                    "Flowt warning",
                    message
            );
        });
        mailThread.start();
        notificationService.addNotification(
                NotificationType.WARNING,
                message,
                report.getWhom()
        );
        return true;
    }

    private String generateCode() {
        String uuid;
        do {
            uuid = UUID.randomUUID().toString();
        } while (verifyCodeRepository.existsByCode(uuid));
        return uuid;
    }
}
