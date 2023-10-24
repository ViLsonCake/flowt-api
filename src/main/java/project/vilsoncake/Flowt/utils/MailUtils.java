package project.vilsoncake.Flowt.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.vilsoncake.Flowt.entity.SongEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.entity.enumerated.NotificationType;
import project.vilsoncake.Flowt.service.MailVerifyService;
import project.vilsoncake.Flowt.service.NotificationService;

import static project.vilsoncake.Flowt.constant.MessageConst.*;

@Component
@RequiredArgsConstructor
public class MailUtils {

    private final MailVerifyService mailVerifyService;
    private final NotificationService notificationService;

    public boolean sendCongratulationsMessagesIfNeed(SongEntity song, UserEntity user) {
        if (song.getListens() % 10_000 != 0) {
            return false;
        }

        // If listens divisible by 10,000. Example 30,000, 50,000, 120,000, send mail and add notification with congratulations
        notificationService.addNotification(
                NotificationType.INFO,
                String.format(SONG_CONGRATULATIONS_MESSAGE, user.getUsername(), song.getName(), song.getListens()),
                user
        );
        Thread mailThread = new Thread(() -> mailVerifyService.sendMessage(
                user.getEmail(),
                SONG_CONGRATULATIONS_SUBJECT,
                String.format(SONG_CONGRATULATIONS_MESSAGE, user.getUsername(), song.getName(), song.getListens())
        ));
        mailThread.start();

        return true;
    }

    public String generateVerifyNotificationMessage(String mailAddress) {
        return String.format(VERIFY_NOTIFICATION_MESSAGE, blurMail(mailAddress));
    }

    public String generateSuccessVerifyEmailMessage(String mailAddress) {
        return String.format(SUCCESS_VERIFY_NOTIFICATION_MESSAGE, blurMail(mailAddress));
    }

    public String generateAlreadyVerifiedEmailMessage(String mailAddress) {
        return String.format(ALREADY_VERIFIED_NOTIFICATION_MESSAGE, blurMail(mailAddress));
    }

    public String blurMail(String mailAddress) {
        String[] splitMailAddress = mailAddress.split("@");
        StringBuilder stringBuilder = new StringBuilder(splitMailAddress[0]);

        if (splitMailAddress.length < 2) {
            return null;
        }

        for (int index = 0; index < stringBuilder.length(); index++) {
            if (index < stringBuilder.length() - 3) {
                stringBuilder.setCharAt(index, BLUR_SYMBOL);
            }
        }

        return stringBuilder + splitMailAddress[1];
    }
}
