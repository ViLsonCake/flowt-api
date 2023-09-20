package project.vilsoncake.Flowt.constant;

public class MessageConst {
    public static final String VERIFY_EMAIL_SUBJECT = "Verify Your Email";
    public static final String VERIFY_EMAIL_TEXT =
            "Hi %s,\n" +
            "Follow this link and verify your Flowt account: \n" +
            "%s";
    public static final String RESTORE_PASSWORD_SUBJECT = "Restore password code";
    public static final String RESTORE_PASSWORD_TEXT =
            "Hi %s, \n" +
            "Enter this code to restore your password: \n" +
            "%s";
    public static final String WARNING_USERNAME_SUBJECT = "Flowt rules";
    public static final String WARNING_USERNAME_TEXT =
            "Hello %s, \n" +
            "Your username contains insult, threat, or prohibited words," +
            " you have 3 days to change it or your account will be automatically blocked.";
    public static final String FOLLOW_MESSAGE = "User %s has followed you";
    public static final String VERIFY_NOTIFICATION_MESSAGE = "Verify your email %s";
    public static final String SUCCESS_VERIFY_NOTIFICATION_MESSAGE = "Email %s successfully verified";
    public static final String ALREADY_VERIFIED_NOTIFICATION_MESSAGE = "Email %s is already verified";
    public static final char BLUR_SYMBOL = '*';
}
