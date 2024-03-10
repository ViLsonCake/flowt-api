package project.vilsoncake.Flowt.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import project.vilsoncake.Flowt.property.MailProperties;
import project.vilsoncake.Flowt.service.MailVerifyService;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import static project.vilsoncake.Flowt.constant.UrlConst.*;

@Service
@RequiredArgsConstructor
public class MailVerifyServiceImpl implements MailVerifyService {
    private final JavaMailSender mailSender;
    private final MailProperties mailProperties;

    @Override
    public boolean sendMessage(String recipient, String subject, String htmlTemplate) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        message.setFrom(mailProperties.getUsername());
        message.setRecipients(MimeMessage.RecipientType.TO, recipient);
        message.setSubject(subject);
        message.setContent(htmlTemplate, "text/html; charset=utf-8");

        mailSender.send(message);
        return true;
    }

    @Override
    public String readFile(String filename) throws IOException {
        Resource resource = new ClassPathResource(filename);
        return resource.getContentAsString(Charset.defaultCharset());
    }

    @Override
    public String insertValuesInTemplate(String templateName, String htmlTemplate, Map<String, String> values) {
        switch (templateName) {
            case VERIFY_EMAIL_TEMPLATE:
                if (values.containsKey("username")) {
                    htmlTemplate = htmlTemplate.replace("${username}", values.get("username"));
                }
                if (values.containsKey("url")) {
                    htmlTemplate = htmlTemplate.replace("${verify-url}", values.get("url"));
                }
                break;
            case RESTORE_PASSWORD_TEMPLATE:
                if (values.containsKey("username")) {
                    htmlTemplate = htmlTemplate.replace("${username}", values.get("username"));
                }
                if (values.containsKey("code")) {
                    htmlTemplate = htmlTemplate.replace("${code}", values.get("code"));
                }
                break;
            case REPORT_TEMPLATE:
                if (values.containsKey("username")) {
                    htmlTemplate = htmlTemplate.replace("${username}", values.get("username"));
                }
                if (values.containsKey("message")) {
                    htmlTemplate = htmlTemplate.replace("${message}", values.get("message"));
                }
                break;
            case VERIFY_ARTIST_TEMPLATE:
                if (values.containsKey("username")) {
                    htmlTemplate = htmlTemplate.replace("${username}", values.get("username"));
                }
                break;
            case SONG_CONGRATULATIONS_TEMPLATE:
                if (values.containsKey("username")) {
                    htmlTemplate = htmlTemplate.replace("${username}", values.get("username"));
                }
                if (values.containsKey("songName")) {
                    htmlTemplate = htmlTemplate.replace("${songName}", values.get("songName"));
                }
                if (values.containsKey("listensCount")) {
                    htmlTemplate = htmlTemplate.replace("${listensCount}", values.get("listensCount"));
                }
                break;
        }

        return htmlTemplate;
    }
}
