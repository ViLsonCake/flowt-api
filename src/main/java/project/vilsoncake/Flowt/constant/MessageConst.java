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
    public static final String SONG_CONGRATULATIONS_SUBJECT = "Congratulations!";
    public static final String SONG_CONGRATULATIONS_MESSAGE = "Congratulations %s! Your song \"%s\" has already gotten over %s listens.";
    public static final char BLUR_SYMBOL = '*';
    public static final String USER_NAME_WARNING_MESSAGE = "Your username violates the rules of our platform, you must change it within 3 days, otherwise your account will be blocked.";
    public static final String USER_DESCRIPTION_WARNING_MESSAGE = "Your description violates the rules of our platform, you must change it within 3 days, otherwise your account will be blocked.";
    public static final String SONG_NAME_WARNING_MESSAGE = "A \"%s\" song title violates the rules of our platform, you must change it within 3 days, otherwise the song will be deleted.";
    public static final String SONG_CONTENT_WARNING_MESSAGE = "Your song \"%s\" violates our platform rules and has been deleted.";
    public static final String USER_AVATAR_WARNING_MESSAGE = "Your avatar violates our platform rules and has been replaced by default.";
    public static final String SONG_AVATAR_WARNING_MESSAGE = "Avatar of your song \"%s\" violates our platform rules and has been replaced by default.";
    public static final String PLAYLIST_NAME_WARNING_MESSAGE = "Name of your playlist \"%s\" violates our platform rules and has been replaced by default.";
    public static final String PLAYLIST_AVATAR_WARNING_MESSAGE = "Avatar of your playlist \"%s\" violates our platform rules and has been replaced by default.";
}
