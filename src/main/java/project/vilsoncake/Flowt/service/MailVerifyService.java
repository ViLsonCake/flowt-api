package project.vilsoncake.Flowt.service;

public interface MailVerifyService {
    boolean sendMessage(String recipient, String subject, String body);
}
