package project.vilsoncake.Flowt.service;

import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.Map;

public interface MailVerifyService {
    boolean sendMessage(String recipient, String subject, String body) throws MessagingException, IOException;
    String readFile(String filename) throws IOException;
    String insertValuesInTemplate(String templateName, String htmlTemplate, Map<String, String> values);
}
