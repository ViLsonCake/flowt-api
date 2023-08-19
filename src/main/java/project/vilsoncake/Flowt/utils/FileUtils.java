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

    public boolean isValidExtension(String filename) {
        String[] splitFilename = filename.split("\\.");
        if (splitFilename.length < 2) return false;

        String extension = splitFilename[splitFilename.length - 1];

        return extension.equalsIgnoreCase("png") ||
                extension.equalsIgnoreCase("jpg") ||
                extension.equalsIgnoreCase("jpeg");
    }
}
