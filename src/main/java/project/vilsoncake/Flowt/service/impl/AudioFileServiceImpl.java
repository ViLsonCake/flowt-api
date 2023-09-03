package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.entity.AudioFileEntity;
import project.vilsoncake.Flowt.entity.SongEntity;
import project.vilsoncake.Flowt.repository.AudioFileRepository;
import project.vilsoncake.Flowt.service.AudioFileService;

@Service
@RequiredArgsConstructor
public class AudioFileServiceImpl implements AudioFileService {

    private final AudioFileRepository audioFileRepository;

    @Override
    public boolean saveFile(String uuid, MultipartFile file, SongEntity song) {
        AudioFileEntity audioFile = new AudioFileEntity(uuid, String.valueOf(file.getSize()), song);
        audioFileRepository.save(audioFile);
        return true;
    }

    @Override
    public AudioFileEntity getBySong(SongEntity song) {
        return audioFileRepository.findBySong(song);
    }

    @Override
    public boolean existsBySong(SongEntity song) {
        return audioFileRepository.existsBySong(song);
    }
}
