package project.vilsoncake.Flowt.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import project.vilsoncake.Flowt.entity.enumerated.NotificationType;
import project.vilsoncake.Flowt.service.ChangeUserService;
import project.vilsoncake.Flowt.service.NotificationService;
import project.vilsoncake.Flowt.service.RedisService;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableScheduling
@Slf4j
@RequiredArgsConstructor
public class ScheduledConfig {

    private final RedisService redisService;
    private final ChangeUserService changeUserService;
    private final NotificationService notificationService;

    @Transactional
    @PostConstruct
    @Scheduled(fixedDelay = 12, timeUnit = TimeUnit.HOURS)
    public void autoBlockUsers() {
        Set<String> keys = redisService.getAllWarningKeys();

        keys.forEach((key) -> {
            String username = getUsernameFromKey(key);
            String value = redisService.getValueFromWarning(username);
            if (value != null && new Date().after(new Date(Long.parseLong(value)))) {
                // Block user
                log.info("User {} blocked", username);
                changeUserService.changeUserActive(username);
                // Remove from warning list
                redisService.deleteByKeyFromWarning(username);
                // Remove warning notification
                notificationService.removeNotificationByType(NotificationType.WARNING);
            }
        });
    }

    private String getUsernameFromKey(String key) {
        String[] splitUsername = key.split(":");
        return splitUsername[splitUsername.length - 1];
    }
}
