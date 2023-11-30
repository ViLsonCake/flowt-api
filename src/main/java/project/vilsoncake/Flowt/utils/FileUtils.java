package project.vilsoncake.Flowt.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static project.vilsoncake.Flowt.constant.PatternConst.VALID_AUDIO_FILE_EXTENSIONS;

@Component
@RequiredArgsConstructor
public class FileUtils {

    public boolean isValidAvatarExtension(String filename) {
        String[] splitFilename = filename.split("\\.");
        if (splitFilename.length < 2) return false;

        String extension = splitFilename[splitFilename.length - 1];

        return extension.equalsIgnoreCase("png") ||
                extension.equalsIgnoreCase("jpg") ||
                extension.equalsIgnoreCase("jpeg");
    }

    public boolean isValidAudioFileExtension(String filename) {
        String[] splitFilename = filename.split("\\.");
        if (splitFilename.length < 2) return false;

        String extension = splitFilename[splitFilename.length - 1];
        return VALID_AUDIO_FILE_EXTENSIONS.contains(extension);
    }
}
