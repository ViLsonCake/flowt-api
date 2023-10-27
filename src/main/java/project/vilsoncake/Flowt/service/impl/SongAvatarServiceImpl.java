package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.entity.SongAvatarEntity;
import project.vilsoncake.Flowt.entity.SongEntity;
import project.vilsoncake.Flowt.repository.SongAvatarRepository;
import project.vilsoncake.Flowt.service.AvatarService;

@Service("songAvatarService")
@Slf4j
@RequiredArgsConstructor
public class SongAvatarServiceImpl implements AvatarService {

    private final SongAvatarRepository songAvatarRepository;

    @Override
    public boolean deleteAvatar(Object entity) {
        SongAvatarEntity songAvatar = songAvatarRepository.findBySong((SongEntity) entity);
        songAvatarRepository.delete(songAvatar);
        return true;
    }
}
