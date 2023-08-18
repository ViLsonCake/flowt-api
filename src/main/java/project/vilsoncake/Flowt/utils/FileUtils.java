package project.vilsoncake.Flowt.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.vilsoncake.Flowt.repository.UserAvatarRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileUtils {

    private final UserAvatarRepository userAvatarRepository;

    public String generateRandomUUID() {
        String uuid;
        do {
            uuid = UUID.randomUUID().toString();
        } while (userAvatarRepository.existsByFilename(uuid));
        return uuid;
    }
}
