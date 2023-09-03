package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.entity.SongAvatarEntity;
import project.vilsoncake.Flowt.entity.SongEntity;
import project.vilsoncake.Flowt.repository.SongAvatarRepository;
import project.vilsoncake.Flowt.service.AvatarService;

@Service
@Slf4j
@RequiredArgsConstructor
public class SongAvatarServiceImpl implements AvatarService {

    private final SongAvatarRepository songAvatarRepository;

    @Override
    public boolean saveAvatar(MultipartFile avatar, String uuid, Object song) {
        SongAvatarEntity songAvatar = new SongAvatarEntity(uuid, String.valueOf(avatar.getSize()), (SongEntity) song);
        songAvatarRepository.save(songAvatar);
        return true;
    }

    @Override
    public boolean existsByFilename(String filename) {
        return songAvatarRepository.existsByFilename(filename);
    }

    @Override
    public boolean existsByEntity(Object entity) {
        return songAvatarRepository.existsBySong((SongEntity) entity);
    }

    @Override
    public SongAvatarEntity getByEntity(Object entity) {
        return songAvatarRepository.findBySong((SongEntity) entity);
    }
}
