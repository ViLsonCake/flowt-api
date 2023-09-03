package project.vilsoncake.Flowt.service;

import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.entity.AudioFileEntity;
import project.vilsoncake.Flowt.entity.SongEntity;

public interface AudioFileService {
    boolean saveFile(String uuid, MultipartFile file, SongEntity song);
    boolean existsBySong(SongEntity song);
    AudioFileEntity getBySong(SongEntity song);
}
