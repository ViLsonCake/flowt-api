package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.vilsoncake.Flowt.config.AppConfig;
import project.vilsoncake.Flowt.dto.RestorePasswordResponse;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.entity.VerifyCodeEntity;
import project.vilsoncake.Flowt.exception.AccountAlreadyVerifiedException;
import project.vilsoncake.Flowt.exception.VerifyCodeNotFoundException;
import project.vilsoncake.Flowt.repository.UserRepository;
import project.vilsoncake.Flowt.repository.VerifyCodeRepository;
import project.vilsoncake.Flowt.service.MailVerifyService;
import project.vilsoncake.Flowt.service.RedisService;
import project.vilsoncake.Flowt.service.UserVerifyService;

import java.util.Map;
import java.util.UUID;

import static project.vilsoncake.Flowt.constant.MessageConst.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserVerifyServiceImpl implements UserVerifyService {

    private final VerifyCodeRepository verifyCodeRepository;
    private final UserRepository userRepository;
    private final MailVerifyService mailVerifyService;
    private final RedisService redisService;
    private final AppConfig appConfig;

    @Override
    public Map<String, String> saveAndSendNewCode(UserEntity user) {
        // Generate new code and save
        String userVerifyCode = generateCode();
        if (!verifyCodeRepository.existsByUser(user)) {
            VerifyCodeEntity verifyCodeEntity = new VerifyCodeEntity(userVerifyCode, user);
            verifyCodeRepository.save(verifyCodeEntity);

            // Send verify mail
            Thread mailThread = new Thread(() -> {
            mailVerifyService.sendMessage(
                    user.getEmail(),
                    VERIFY_EMAIL_SUBJECT,
                    String.format(
                            VERIFY_EMAIL_TEXT,
                            user.getUsername(),
                            (appConfig.getVerifyUrl() + userVerifyCode)
                    )
            );
            });
            mailThread.start();

            return Map.of("username", user.getUsername());
        }
        return Map.of("error", "Token not found");
    }

    @Override
    public Map<String, String> verifyUser(String code) throws VerifyCodeNotFoundException, AccountAlreadyVerifiedException {
        VerifyCodeEntity verifyCodeEntity = verifyCodeRepository.findByCode(code).orElseThrow(() ->
                new VerifyCodeNotFoundException("Verify code don't exist"));

        UserEntity user = verifyCodeEntity.getUser();

        if (user.isEmailVerify()) throw new AccountAlreadyVerifiedException("Account already verified");

        user.setEmailVerify(true);
        userRepository.save(user);

        return Map.of("username", user.getUsername());
    }

    @Override
    public Map<String, String> sendChangePasswordMessageByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
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
    public RestorePasswordResponse sendChangePasswordMessageByEmail(String email) {
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

        return new RestorePasswordResponse(email);
    }

    @Override
    public Map<String, String> sendWarningMessage(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));

        // Sent mail
        Thread mailThread = new Thread(() -> {
            mailVerifyService.sendMessage(
                    user.getEmail(),
                    WARNING_USERNAME_SUBJECT,
                    String.format(
                            WARNING_USERNAME_TEXT,
                            user.getUsername()
                    )
            );
        });
        mailThread.start();

        // Add warning to redis
        redisService.setValueToWarning(username, String.valueOf(System.currentTimeMillis() + daysToMillis(3)));

        return Map.of("message", "Mail sent");
    }

    private String generateCode() {
        String uuid;
        do {
            uuid = UUID.randomUUID().toString();
        } while (verifyCodeRepository.existsByCode(uuid));
        return uuid;
    }

    private Long daysToMillis(int days) {
        return (long) days * 24 * 60 * 60 * 1000;
    }
}
