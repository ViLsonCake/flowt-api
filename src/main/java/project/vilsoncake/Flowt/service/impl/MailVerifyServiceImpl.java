package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import project.vilsoncake.Flowt.properties.MailProperties;
import project.vilsoncake.Flowt.service.MailVerifyService;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailVerifyServiceImpl implements MailVerifyService {
    private final JavaMailSender mailSender;
    private final MailProperties mailProperties;

    @Override
    public boolean sendMessage(String recipient, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailProperties.getUsername());
        message.setTo(recipient);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
        log.info("Verify message send to {}", recipient);
        return true;
    }
}
