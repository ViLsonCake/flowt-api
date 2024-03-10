package project.vilsoncake.Flowt.service.impl;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.vilsoncake.Flowt.entity.ReportEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.entity.VerifyCodeEntity;
import project.vilsoncake.Flowt.entity.enumerated.NotificationType;
import project.vilsoncake.Flowt.exception.AccountAlreadyVerifiedException;
import project.vilsoncake.Flowt.exception.VerifyCodeNotFoundException;
import project.vilsoncake.Flowt.property.ApplicationProperties;
import project.vilsoncake.Flowt.repository.UserRepository;
import project.vilsoncake.Flowt.repository.VerifyCodeRepository;
import project.vilsoncake.Flowt.service.MailVerifyService;
import project.vilsoncake.Flowt.service.NotificationService;
import project.vilsoncake.Flowt.service.RedisService;
import project.vilsoncake.Flowt.service.UserVerifyService;
import project.vilsoncake.Flowt.utils.AuthUtils;
import project.vilsoncake.Flowt.utils.MailUtils;
import project.vilsoncake.Flowt.utils.ReportUtils;

import java.io.IOException;
import java.util.Map;

import static project.vilsoncake.Flowt.constant.MessageConst.RESTORE_PASSWORD_SUBJECT;
import static project.vilsoncake.Flowt.constant.MessageConst.VERIFY_EMAIL_SUBJECT;
import static project.vilsoncake.Flowt.constant.UrlConst.RESTORE_PASSWORD_TEMPLATE;
import static project.vilsoncake.Flowt.constant.UrlConst.VERIFY_EMAIL_TEMPLATE;

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
    public Map<String, String> sendVerifyMailAndNotification(UserEntity user) throws IOException {
        String htmlTemplate = mailVerifyService.readFile(VERIFY_EMAIL_TEMPLATE);
        String htmlCode = mailVerifyService.insertValuesInTemplate(
                VERIFY_EMAIL_TEMPLATE,
                htmlTemplate,
                Map.of(
                        "username", user.getUsername(),
                        "url", applicationProperties.getVerifyUrl() + user.getVerifyCode().getCode()
                )
        );

        Thread mailThread = new Thread(() -> {
            try {
                mailVerifyService.sendMessage(
                        user.getEmail(),
                        VERIFY_EMAIL_SUBJECT,
                        htmlCode
                );
            } catch (MessagingException | IOException e) {
                throw new RuntimeException(e);
            }
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
    public Map<String, String> sendChangePasswordMessageByUsername(String authHeader) throws IOException {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userRepository.findByUsernameIgnoreCase(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));

        // Generate code and save in redis
        String code = redisService.saveNewPasswordCode(username);

        String htmlTemplate = mailVerifyService.readFile(RESTORE_PASSWORD_TEMPLATE);
        String htmlCode = mailVerifyService.insertValuesInTemplate(
                RESTORE_PASSWORD_TEMPLATE,
                htmlTemplate,
                Map.of(
                        "username", username,
                        "code", code
                )
        );

        Thread mailThread = new Thread(() -> {
            try {
                mailVerifyService.sendMessage(
                        user.getEmail(),
                        RESTORE_PASSWORD_SUBJECT,
                        htmlCode
                );
            } catch (MessagingException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        mailThread.start();

        return Map.of("username", user.getUsername());
    }

    @Override
    public Map<String, String> sendChangePasswordMessageByEmail(String email) throws IOException {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));

        // Generate code and save in redis
        String code = redisService.saveNewPasswordCode(user.getUsername());

        String htmlTemplate = mailVerifyService.readFile(RESTORE_PASSWORD_TEMPLATE);
        String htmlCode = mailVerifyService.insertValuesInTemplate(
                RESTORE_PASSWORD_TEMPLATE,
                htmlTemplate,
                Map.of(
                        "username", user.getUsername(),
                        "code", code
                )
        );

        Thread mailThread = new Thread(() -> {
            try {
                mailVerifyService.sendMessage(
                        user.getEmail(),
                        RESTORE_PASSWORD_SUBJECT,
                        htmlCode
                );
            } catch (MessagingException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        mailThread.start();

        return Map.of("email", email);
    }

    @Override
    public boolean sendWarningMessage(ReportEntity report) throws IOException {
        String message = reportUtils.createReportMessage(report);

        String htmlTemplate = mailVerifyService.readFile(RESTORE_PASSWORD_TEMPLATE);
        String htmlCode = mailVerifyService.insertValuesInTemplate(
                RESTORE_PASSWORD_TEMPLATE,
                htmlTemplate,
                Map.of(
                        "username", report.getWhom().getUsername(),
                        "message", message
                )
        );

        Thread mailThread = new Thread(() -> {
            try {
                mailVerifyService.sendMessage(
                        report.getWhom().getEmail(),
                        "Flowt warning",
                        htmlCode
                );
            } catch (MessagingException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        mailThread.start();
        notificationService.addNotification(
                NotificationType.WARNING,
                message,
                report.getWhom()
        );
        return true;
    }
}
