package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.vilsoncake.Flowt.config.AppConfig;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.entity.VerifyCodeEntity;
import project.vilsoncake.Flowt.exception.AccountAlreadyVerifiedException;
import project.vilsoncake.Flowt.exception.VerifyCodeNotFoundException;
import project.vilsoncake.Flowt.repository.UserRepository;
import project.vilsoncake.Flowt.repository.VerifyCodeRepository;
import project.vilsoncake.Flowt.service.MailVerifyService;
import project.vilsoncake.Flowt.service.RedisService;
import project.vilsoncake.Flowt.service.UserVerifyService;

import java.util.Random;
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
    public boolean saveAndSendNewCode(UserEntity user) {
        // Generate new code and save
        String userVerifyCode = generateCode();
        if (!verifyCodeRepository.existsByUser(user)) {
            VerifyCodeEntity verifyCodeEntity = new VerifyCodeEntity(userVerifyCode, user);
            verifyCodeRepository.save(verifyCodeEntity);

            // Send verify mail
            mailVerifyService.sendMessage(
                    user.getEmail(),
                    VERIFY_EMAIL_SUBJECT,
                    String.format(
                            VERIFY_EMAIL_TEXT,
                            user.getUsername(),
                            (appConfig.getVerifyUrl() + userVerifyCode)));

            return true;
        }
        return false;
    }

    @Override
    public boolean verifyUser(String code) throws VerifyCodeNotFoundException, AccountAlreadyVerifiedException {
        VerifyCodeEntity verifyCodeEntity = verifyCodeRepository.findByCode(code).orElseThrow(() ->
                new VerifyCodeNotFoundException("Verify code don't exist"));

        UserEntity user = verifyCodeEntity.getUser();

        if (user.isEmailVerify()) throw new AccountAlreadyVerifiedException("Account already verified");

        user.setEmailVerify(true);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean sendChangePasswordMessage(UserEntity user) {
        // Generate code and save in redis
        Integer randomCode = new Random().nextInt(1000, 9999);
        redisService.setValue(user.getUsername(), String.valueOf(randomCode));

        mailVerifyService.sendMessage(
                user.getEmail(),
                RESTORE_PASSWORD_SUBJECT,
                String.format(
                        RESTORE_PASSWORD_TEXT,
                        user.getUsername(),
                        randomCode
                )
        );

        return true;
    }

    @Override
    public boolean changeUserPasswordByUsername(String username, String password) {
        return false;
    }

    private String generateCode() {
        String uuid;
        do {
            uuid = UUID.randomUUID().toString();
        } while (verifyCodeRepository.existsByCode(uuid));
        return uuid;
    }
}
