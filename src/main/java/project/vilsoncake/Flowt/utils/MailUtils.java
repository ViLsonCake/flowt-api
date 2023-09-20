package project.vilsoncake.Flowt.utils;

import org.springframework.stereotype.Component;
import project.vilsoncake.Flowt.constant.MessageConst;

import static project.vilsoncake.Flowt.constant.MessageConst.BLUR_SYMBOL;
import static project.vilsoncake.Flowt.constant.MessageConst.VERIFY_NOTIFICATION_MESSAGE;

@Component
public class MailUtils {

    public String generateVerifyNotificationMessage(String mailAddress) {
        return String.format(VERIFY_NOTIFICATION_MESSAGE, blurMail(mailAddress));
    }

    public String blurMail(String mailAddress) {
        String[] splitMailAddress = mailAddress.split("@");
        StringBuilder stringBuilder = new StringBuilder(splitMailAddress[0]);

        if (splitMailAddress.length < 2) return null;

        for (int index = 0; index < stringBuilder.length(); index++) {
            if (index < stringBuilder.length() - 3) stringBuilder.setCharAt(index, BLUR_SYMBOL);
        }

        return stringBuilder + splitMailAddress[1];
    }
}
